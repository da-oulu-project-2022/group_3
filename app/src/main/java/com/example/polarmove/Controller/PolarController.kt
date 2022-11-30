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
import androidx.compose.material.Button
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
import kotlin.math.pow
import kotlin.math.sqrt

class PolarController {

    private val applicationContext:Context
    private val componentActivity:ComponentActivity

    private var deviceId = "B5E66523"
    private var connected = false
    private lateinit var api: PolarBleApi

    private val accelValuesArray = mutableStateOf(mutableListOf(intArrayOf(0,0,0)))
    private val accelSumArray = mutableStateOf(intArrayOf(0,0,0))
    private val isInPut = mutableStateOf(false)


    private var sensetivity=300 //Accel in mG
    private var baseline = 1000 //Accel in mG
    private var inputDelay=200 //time in ms

    //https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
    //val inputChange: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    private val _inputLeft: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val inputLeft = _inputLeft.asStateFlow()

    private val _inputRight: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val inputRight = _inputRight.asStateFlow()

    private val _inputUp: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val inputUp = _inputUp.asStateFlow()

    private val _inputDown: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val inputDown = _inputDown.asStateFlow()




    val connectionStateText = mutableStateOf("text")


    companion object {
        const val TAG = "Polar_MainActivity"
        const val BTTAG = "Bluetooth_Code"
        private const val SHARED_PREFS_KEY = "polar_device_id"
        private const val PERMISSION_REQUEST_CODE = 1
    }

    private val bluetoothOnActivityResultLauncher: ActivityResultLauncher<Intent>

    constructor(context: Context,activity: ComponentActivity){
        applicationContext=context
        componentActivity=activity

        bluetoothOnActivityResultLauncher = componentActivity.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode != Activity.RESULT_OK) {
                Log.w(TAG, "Bluetooth off")
            }
        }

        api = PolarBleApiDefaultImpl.defaultImplementation(applicationContext, PolarBleApi.ALL_FEATURES)

        //api = apy

        mySetup()

    }

    private fun mySetup(){
        checkBT()

        api.setApiCallback(object : PolarBleApiCallback() {
            override fun blePowerStateChanged(blePowerState: Boolean) {
                Log.d(BTTAG, "BluetoothStateChanged $blePowerState")
            }

            override fun deviceConnected(polarDeviceInfo: PolarDeviceInfo) {
                Log.d(BTTAG, "Device connected " + polarDeviceInfo.deviceId)
                connectionStateText.value="Connected"

                Toast.makeText(applicationContext, "Connected", Toast.LENGTH_SHORT).show()
            }

            override fun deviceConnecting(polarDeviceInfo: PolarDeviceInfo) {
                Log.d(BTTAG, "Device connecting ${polarDeviceInfo.deviceId}")
                connectionStateText.value="Connecting"

            }
        }
        )

        //api.connectToDevice(deviceId)

    }

    public fun connectToDevice(id:String){
        if(!connected){

            try {
                connected = true

                api.connectToDevice(id)



            } catch (polarInvalidArgument: PolarInvalidArgument) {
                val attempt = "connection Failed"

                Log.e(BTTAG, "Failed to $attempt. Reason $polarInvalidArgument ")

                connected = false

            }
        }
    }

    @Composable
    public fun showStartStreamButton(){

        Button(onClick = { startStream() }) {

        }
        //accButton.setOnClickListener {
        //    accelButtonOnClick();
        //}

    }

    private fun startStream(){
        api.requestStreamSettings(deviceId, PolarBleApi.DeviceStreamingFeature.ACC).toFlowable().flatMap {
                settings:PolarSensorSetting -> api.startAccStreaming(deviceId, settings)}
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    Log.d(BTTAG,"Message received")
                    onAccelReceive(it)

                },
                { error: Throwable ->
                    //toggleButtonUp(accButton, R.string.start_acc_stream)
                    Log.e(MainActivity.TAG, "ACC stream failed. Reason $error")
                },
                {
                    showToast("ACC stream complete")
                    Log.d(MainActivity.TAG, "ACC stream complete")
                }
            )
        Log.d(BTTAG,"Subscribed")
    }
    private fun accelButtonOnClick(){

        api.requestStreamSettings(deviceId, PolarBleApi.DeviceStreamingFeature.ACC).toFlowable().flatMap {
                settings:PolarSensorSetting -> api.startAccStreaming(deviceId, settings)}
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    onAccelReceive(it)

                },
                { error: Throwable ->
                    //toggleButtonUp(accButton, R.string.start_acc_stream)
                    Log.e(TAG, "ACC stream failed. Reason $error")
                },
                {
                    showToast("ACC stream complete")
                    Log.d(TAG, "ACC stream complete")
                }
            )
    }

    val lastFullSegment= mutableStateOf(Vector3D())
    private val currentSegmentVector3d = Vector3D()
    private var currentSegmentSamples=0
    private val maxSegmentSize=5


    private fun onAccelReceive(polarAccelerometerData: PolarAccelerometerData){

        accelValuesArray.value.clear()
        val segmentTag="SegmentVector"

        for (data in polarAccelerometerData.samples) {

            currentSegmentVector3d.add(data.x,data.y,data.z)
            currentSegmentSamples++
            if (currentSegmentSamples>=maxSegmentSize){
                currentSegmentVector3d/=maxSegmentSize

                Log.d(segmentTag,"X: ${currentSegmentVector3d.x} Y: ${currentSegmentVector3d.y} Z: ${currentSegmentVector3d.z} ")

                if (currentSegmentVector3d.length()<baseline-sensetivity||currentSegmentVector3d.length()>baseline+sensetivity){
                    _inputLeft.value = true
                    Log.d(segmentTag,"true")
                }else {
                    _inputLeft.value = false
                    Log.d(segmentTag,"false")
                }

                lastFullSegment.value=currentSegmentVector3d
                currentSegmentVector3d.clear()
                currentSegmentSamples=0
            }

            accelValuesArray.value.add(intArrayOf(data.x,data.y,data.z))
        }
/*
        segmentsState.value.clear()

        for (data in segmentsVariable){
            segmentsState.value.add(data)
        }
        */

        var accelSumm:IntArray= intArrayOf(0,0,0)

        for (data in accelValuesArray.value){
            accelSumm[0] += data[0]
            accelSumm[1] += data[1]
            accelSumm[2] += data[2]
        }

        accelSumm[0] /= accelValuesArray.value.size
        accelSumm[1] /= accelValuesArray.value.size
        accelSumm[2] /= accelValuesArray.value.size

        Log.d("Sensor_Data","X: ${accelSumm[0]} Y: ${accelSumm[1]} Z: ${accelSumm[2]} ")

        accelSumArray.value = accelSumm

        val squaredSum = accelSumm.map { n: Int -> n * n }
        val lengthSum = sqrt((squaredSum[0]+squaredSum[1]+squaredSum[2]).toDouble())

        isInPut.value = isValidInput(accelSumm)

    }

    @Composable
    fun AccelDataDisplay() {
        val theValues by accelSumArray

        Row {
            Column {
                Text("X: ${theValues[0]}")
                Text("Y: ${theValues[1]}")
                Text("Z: ${theValues[2]}")
                Text("Sum: ${length(theValues)}")

                Text("Is Input ${isValidInput(theValues)}")


            }
        }
    }

    @Composable
    fun ConnectionStatusText() {
        val myText by connectionStateText
        // or
        //val myText by text.collectAsState()
        Text(myText)
    }

    private fun length(array:IntArray): Double {
        return sqrt(array[0].toDouble().pow(2) +array[1].toDouble().pow(2) +array[2].toDouble().pow(2))
    }

    private fun isValidInput (input:Vector3D): Boolean {
        return ((input.length()>2000.0)||(input.length()<0.0))
    }
    private fun isValidInput (input:IntArray): Boolean {
        return ((length(input)>1500.0)||(length(input)<500.0))
    }

    private fun checkBT() {
        val btManager = applicationContext.getSystemService(ComponentActivity.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter: BluetoothAdapter? = btManager.adapter
        if (bluetoothAdapter == null) {
            showToast("Device doesn't support Bluetooth")
            return
        }

        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            bluetoothOnActivityResultLauncher.launch(enableBtIntent)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    componentActivity.requestPermissions(arrayOf(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT), PERMISSION_REQUEST_CODE)
                } else {
                    componentActivity.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
                }
            } else {
                componentActivity.requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_REQUEST_CODE)
            }
        }
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(applicationContext, message, Toast.LENGTH_LONG)
        toast.show()
    }

    class DirectionalInput{
        constructor(h:Int,v:Int,f:Int){
            directions[0] = h
            directions[1] = v
            directions[2] = f

        }
        val directions = arrayOf(0,0,0)
    }

}