package com.example.firscomposeapp

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.polar.sdk.api.PolarBleApi
import com.polar.sdk.api.PolarBleApiCallback
import com.polar.sdk.api.PolarBleApiDefaultImpl
import com.polar.sdk.api.errors.PolarInvalidArgument
import com.polar.sdk.api.model.PolarDeviceInfo
import com.polar.sdk.api.model.PolarAccelerometerData
import com.polar.sdk.api.model.PolarSensorSetting
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.unit.sp
import com.example.polarmove.GameVM
import com.polar.sdk.api.model.PolarHrData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PolarController {

    private val applicationContext: Context
    private val componentActivity: ComponentActivity
    private val gameViewModel: GameVM

    private var deviceId = "B5E66523"
    private var connected = false
    private val api: PolarBleApi

    private val accelValuesArray = mutableStateOf(mutableListOf(intArrayOf(0, 0, 0)))
    private val accelSumArray = mutableStateOf(intArrayOf(0, 0, 0))


    private var sensetivity = 350 //Accel in mG
    private var baseline = 1000 //Accel in mG
    private var inputCooldownTime = 800 //time in ms

    //https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
    //val inputChange: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    private val _inputLeft: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val inputLeft = _inputLeft.asStateFlow()

    private val _inputRight: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val inputRight = _inputRight.asStateFlow()

    private val _inputUp: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val inputUp = _inputUp.asStateFlow()

    private val _inputDown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val inputDown = _inputDown.asStateFlow()

    private val _rawData: MutableStateFlow<IntArray> = MutableStateFlow(intArrayOf(0, 0, 0))
    val rawData = _rawData.asStateFlow()


    val connectionStateText = mutableStateOf("text")


    companion object {
        const val TAG = "PolarController"
        const val TAG_DATA = "PolarController_Data"
        private const val SHARED_PREFS_KEY = "polar_device_id"
        private const val PERMISSION_REQUEST_CODE = 1
    }


    constructor(context: Context, activity: ComponentActivity, gameVM: GameVM) {
        applicationContext = context
        componentActivity = activity
        gameViewModel = gameVM

        api = PolarBleApiDefaultImpl.defaultImplementation(
            applicationContext,
            PolarBleApi.ALL_FEATURES
        )
        mySetup()
    }

    private fun mySetup() {
        //checkBT()

        api.setApiCallback(object : PolarBleApiCallback() {
            override fun blePowerStateChanged(blePowerState: Boolean) {
                Log.d(TAG, "BluetoothStateChanged $blePowerState")
            }

            override fun deviceConnected(polarDeviceInfo: PolarDeviceInfo) {
                Log.d(TAG, "Device connected " + polarDeviceInfo.deviceId)
                connectionStateText.value = "Connected"

                Toast.makeText(applicationContext, "Connected", Toast.LENGTH_SHORT).show()
            }

            override fun deviceConnecting(polarDeviceInfo: PolarDeviceInfo) {
                Log.d(TAG, "Device connecting ${polarDeviceInfo.deviceId}")
                connectionStateText.value = "Connecting"

            }

            override fun batteryLevelReceived(identifier: String, level: Int) {
                Log.d(TAG, "BATTERY LEVEL: $level")
                gameViewModel.batteryLevel.value = level
            }

            override fun hrNotificationReceived(identifier: String, data: PolarHrData) {
                Log.d(TAG, "HR value: ${data.hr} rrsMs: ${data.rrsMs} rr: ${data.rrs} contact: ${data.contactStatus} , ${data.contactStatusSupported}")
                gameViewModel.hr.value = data.hr
            }

        }
        )
        Log.d(TAG, "Setup complete")
    }

    public fun connectToDevice(id: String) {
        if (!connected) {
            deviceId = id

            connected = try {
                api.connectToDevice(id)
                true
            } catch (polarInvalidArgument: PolarInvalidArgument) {
                val attempt = "connection Failed"
                Log.e(TAG, "Failed to $attempt. Reason $polarInvalidArgument ")
                false
            }
        }
    }

    public fun startStream(): Boolean {
        if (!connected) {
            return false;
        }
        api.requestStreamSettings(deviceId, PolarBleApi.DeviceStreamingFeature.ACC).toFlowable()
            .flatMap { settings: PolarSensorSetting -> api.startAccStreaming(deviceId, settings) }
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    Log.d(TAG, "Message received")
                    onAccelReceive(it)
                },
                { error: Throwable ->
                    Log.e(TAG, "ACC stream failed. Reason $error")
                },
                {
                    showToast("ACC stream complete")
                    Log.d(TAG, "ACC stream complete")
                }
            )
        Log.d(TAG, "Subscribed")
        return true
    }

    private fun accelButtonOnClick() {
        api.requestStreamSettings(deviceId, PolarBleApi.DeviceStreamingFeature.ACC).toFlowable()
            .flatMap { settings: PolarSensorSetting -> api.startAccStreaming(deviceId, settings) }
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    onAccelReceive(it)
                },
                { error: Throwable ->
                    Log.e(TAG, "ACC stream failed. Reason $error")
                },
                {
                    showToast("ACC stream complete")
                    Log.d(TAG, "ACC stream complete")
                }
            )
    }

    private val _fullSegment: MutableStateFlow<IntArray> = MutableStateFlow(intArrayOf(0, 0, 0))
    val fullSegment = _fullSegment.asStateFlow()

    private val currentSegmentVector3d = Vector3D()
    private var currentSegmentSamples = 0
    private val maxSegmentSize = 5
    private val lastPackageAverage = Vector3D()

    private val _lastSegmentsList = mutableStateListOf(Vector3D())
    private val lastSegmentsList: List<Vector3D> = _lastSegmentsList

    private fun onAccelReceive(polarAccelerometerData: PolarAccelerometerData) {

        accelValuesArray.value.clear()
        _lastSegmentsList.clear()

        for (data in polarAccelerometerData.samples) {

            _rawData.value = intArrayOf(data.x, data.y, data.z)
            currentSegmentVector3d.add(data.x, data.y, data.z)
            lastPackageAverage.add(data.x, data.y, data.z)
            currentSegmentSamples++

            if (currentSegmentSamples >= maxSegmentSize) {
                processFullSegment()
            }

            accelValuesArray.value.add(intArrayOf(data.x, data.y, data.z))
        }
        lastPackageAverage /= polarAccelerometerData.samples.size
        accelSumArray.value = lastPackageAverage.toIntArray()
    }

    private fun processFullSegment() {

        currentSegmentVector3d /= maxSegmentSize
        Log.d(TAG_DATA, printInput(currentSegmentVector3d))

        CheckIfValidInput(currentSegmentVector3d)

        _fullSegment.value = currentSegmentVector3d.toIntArray()
        _lastSegmentsList.add(Vector3D(currentSegmentVector3d))

        currentSegmentVector3d.clear()
        currentSegmentSamples = 0

    }
    val TAG_INPUT = "PolarController_InputValidation"
    var inputOnCooldown = false
    private suspend fun inputCooldown() {
        delay(inputCooldownTime.toLong())
        Log.d(TAG_INPUT,"Can move again")
        inputOnCooldown = false
    }

    private fun CheckIfValidInput(input: Vector3D) {

        Log.d(TAG_DATA, "Before rotation " + printInput(input))

        Log.d(TAG_DATA, "Rotate by ${offsetRotation[0]} ${offsetRotation[1]} ${offsetRotation[2]}")
        input.rotate(offsetRotation)
        _lastSegmentsList.add(Vector3D(input))
        Log.d(TAG_DATA, "After rotation " + printInput(input))

        checkInputDirection(_inputRight, input.x, sensetivity)
        checkInputDirection(_inputLeft, -input.x, sensetivity)
        checkInputDirection(_inputUp, (input.z-baseline), (sensetivity))
        checkInputDirection(_inputDown, -(input.z-baseline), (sensetivity))

        /*
        _inputLeft.value = input.x < -sensetivity
        _inputUp.value = input.z > sensetivity + baseline
        _inputDown.value = input.x < -sensetivity + baseline
        */
    }

    private fun checkInputDirection(direction: MutableStateFlow<Boolean>, input: Double, sens: Int)
    {
        val before = direction.value
        if (inputOnCooldown) {
            return
        }
        if (input > sens) {
            direction.value = true
            if (before!=direction.value){
                Log.d(TAG_INPUT,"Cant move for now")
                inputOnCooldown = true
                GlobalScope.launch { inputCooldown() }
            }

        } else {
            direction.value = false
        }
    }

    private fun printInput(vector3D: Vector3D): String {
        return "X: ${vector3D.x} Y: ${vector3D.y} Z: ${vector3D.z}"
    }

    private val downVector3D = Vector3D(0, 0, -1)
    private val offset = Vector3D()
    private var offsetRotation = doubleArrayOf(0.0, 0.0, 0.0)

    val TAG_CALIBRATION = "Calibration"

    public fun CalibrateSensor() {
        offset.set(lastPackageAverage)
        offsetRotation = offset.rotationFrom(downVector3D)
        Log.d(TAG_CALIBRATION, "rotation is " + printInput(Vector3D(offsetRotation)))
    }

    @Composable
    fun AccelDataDisplay() {
        val theValues = lastSegmentsList
        val fS = 5
        var i = 0
        Row {
            while (i < theValues.size) {
                if (i % 2 == 0)
                    Column {

                        Text("X: ${theValues[i].x.toInt()}", fontSize = fS.sp)
                        Text("Y: ${theValues[i].y.toInt()}", fontSize = fS.sp)
                        Text("Z: ${theValues[i].z.toInt()}", fontSize = fS.sp)
                        Text("Sum: ${theValues[i].length().toInt()}", fontSize = fS.sp)

                    }
                i++
            }
        }
        i = 0
        Row {
            while (i < theValues.size) {
                if (i % 2 == 1)
                    Column {
                        Text(text = "X: ${theValues[i].x.toInt()}", fontSize = fS.sp)
                        Text("Y: ${theValues[i].y.toInt()}", fontSize = fS.sp)
                        Text("Z: ${theValues[i].z.toInt()}", fontSize = fS.sp)
                        Text("Sum: ${theValues[i].length().toInt()}", fontSize = fS.sp)
                    }
                i++
            }
        }
    }

    @Composable
    fun ConnectionStatusText() {
        val myText by connectionStateText
        Text(myText)
    }


    private fun showToast(message: String) {
        val toast = Toast.makeText(applicationContext, message, Toast.LENGTH_LONG)
        toast.show()
    }
}