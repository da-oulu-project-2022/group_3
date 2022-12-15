package com.example.firscomposeapp

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.PI

class PolarController {

    private val applicationContext: Context
    private val componentActivity: ComponentActivity

    private var deviceId = "B5E66523"
    private var connected = false
    private val api: PolarBleApi

    private val accelValuesArray = mutableStateOf(mutableListOf(intArrayOf(0, 0, 0)))
    private val accelSumArray = mutableStateOf(intArrayOf(0, 0, 0))


    private var sensetivity = 450 //Accel in mG
    private var baseline = 1000 //Accel in mG
    private var inputCooldownTime = 500 //time in ms

    private val orientation = Orientation()

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


    constructor(context: Context, activity: ComponentActivity) {
        applicationContext = context
        componentActivity = activity

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
        }
        )
        Log.d(TAG, "Setup complete")
    }

    public fun connectToDevice(id: String) {
        if (!connected) {
            Log.d(TAG,"Connecting to $id")
            deviceId = id

            try {
                connected = true
                api.connectToDevice(id)

            } catch (polarInvalidArgument: PolarInvalidArgument) {
                val attempt = "connection Failed"
                Log.e(TAG, "Failed to $attempt. Reason $polarInvalidArgument ")
                connected = false
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

    private var lastPacketTimePolar:Long = 0
    private var lastPackageTimeAndroid: Long =0
    private val milliSecondsToNanoSeconds = 1000000

    private fun onAccelReceive(polarAccelerometerData: PolarAccelerometerData) {
        val thisPackageTimeAndroid = System.currentTimeMillis()
        val thisPacketTimePolar = polarAccelerometerData.timeStamp/milliSecondsToNanoSeconds

        accelValuesArray.value.clear()
        _lastSegmentsList.clear()
        val sampleAmounts = polarAccelerometerData.samples.size
        var xtSample =0
        for (data in polarAccelerometerData.samples) {
            xtSample++
            _rawData.value = intArrayOf(data.x, data.y, data.z)
            currentSegmentVector3d.add(data.x, data.y, data.z)
            lastPackageAverage.add(data.x, data.y, data.z)
            currentSegmentSamples++

            if (currentSegmentSamples >= maxSegmentSize) {
                val diff = (thisPacketTimePolar-lastPacketTimePolar)
                val percentage = (diff*(xtSample.toDouble()/sampleAmounts.toDouble())).toLong()
                Log.d(TAG_INPUT,"Polar Time Recieved: $thisPacketTimePolar Android Time Recieved $thisPackageTimeAndroid")
                Log.d(TAG_INPUT,"Diff: $diff percentage: $percentage")
                Log.d(TAG_INPUT,"React to ${thisPackageTimeAndroid+percentage}")
                Log.d(TAG_INPUT,"Last Polar Time Recieved: $lastPacketTimePolar Last Android Time Recieved $lastPackageTimeAndroid")

                processFullSegment(thisPackageTimeAndroid+percentage)
            }

            accelValuesArray.value.add(intArrayOf(data.x, data.y, data.z))
        }

        lastPackageAverage /= polarAccelerometerData.samples.size
        accelSumArray.value = lastPackageAverage.toIntArray()
        lastPacketTimePolar=(thisPacketTimePolar)
        lastPackageTimeAndroid = thisPackageTimeAndroid


    }

    private fun processFullSegment(timeStamp:Long) {

        currentSegmentVector3d /= maxSegmentSize
        Log.d(TAG_DATA, printInput(currentSegmentVector3d))

        Log.d(TAG_INPUT,"recieved $timeStamp suspenduntil $suspendInputUntill")
        orientation.alignVector(currentSegmentVector3d)
        if (timeStamp>suspendInputUntill){
            Log.d(TAG_INPUT,"Try for input")
            CheckIfValidInput(currentSegmentVector3d,timeStamp)
        }

        //Log.d(TAG_INPUT, "Before Copy $currentSegmentVector3d")
        _fullSegment.value = currentSegmentVector3d.toIntArray()
        _lastSegmentsList.add(Vector3D(currentSegmentVector3d))

        currentSegmentVector3d.clear()
        currentSegmentSamples = 0

    }
    val TAG_INPUT = "PolarController_InputValidation"
    var inputOnCooldown = false
    var suspendInputUntill:Long =0;
    private suspend fun inputCooldown() {
        delay(inputCooldownTime.toLong())
        Log.d(TAG_INPUT,"Can move again")
        inputOnCooldown = false
    }

    private fun CheckIfValidInput(input: Vector3D,timeStamp: Long) {
        Log.d(TAG_DATA, "Before rotation " + printInput(input))

        Log.d(TAG_DATA, "Rotate by ${offsetRotation[0]} ${offsetRotation[1]} ${offsetRotation[2]}")
        //input.rotate(offsetRotation)
        //orientation.alignVector(input)

        _lastSegmentsList.add(Vector3D(input))

        Log.d(TAG_DATA, "After rotation " + printInput(input))

        checkInputDirection(_inputRight, input.x, sensetivity,timeStamp)
        checkInputDirection(_inputLeft, -input.x, sensetivity,timeStamp)
        checkInputDirection(_inputUp, (input.z-baseline), (sensetivity),timeStamp)
        checkInputDirection(_inputDown, -(input.z-baseline), (sensetivity),timeStamp)
    }

    @Composable
    fun showOrientationSettings(){
        orientation.changeOrientationSetting()
    }
    private class Orientation{
        enum class Direction{Up,Down,Left,Right,TowardsBody,AwayBody}
        var metalButtonDirection = Direction.TowardsBody
        var sensorDirection = Direction.Up

        fun alignVector(vector3D: Vector3D){
            val before = Vector3D(vector3D)
            when(sensorDirection){
                Direction.Up -> {
                    when(metalButtonDirection){
                        Direction.TowardsBody -> {
                            return
                        }
                        Direction.Left -> {
                            vector3D.rotate(0.0,0.0,-0.5* PI)
                        }
                        Direction.AwayBody -> {
                            vector3D.rotate(0.0,0.0,-1* PI)
                        }
                        Direction.Right -> {
                            vector3D.rotate(0.0,0.0,0.5* PI)
                        }
                    }
                }
                Direction.Down -> {
                    when(metalButtonDirection){
                        Direction.TowardsBody -> {
                            vector3D.rotate(PI,0.0, PI)
                        }
                        Direction.Left -> {
                            vector3D.rotate(PI,0.0,0.5* PI)
                        }
                        Direction.AwayBody -> {
                            vector3D.rotate(PI,0.0,0.0)
                        }
                        Direction.Right -> {
                            vector3D.rotate(PI,0.0,-0.5* PI)
                        }
                    }
                }
                Direction.Left -> {
                    when(metalButtonDirection){
                        Direction.TowardsBody -> {
                            vector3D.rotate(0.0,-0.5*PI,0.0)
                        }
                        Direction.Up -> {
                            vector3D.rotate(-0.5*PI,-0.0*PI,0.5*PI)
                        }
                        Direction.AwayBody -> {
                            vector3D.rotate(0.0*PI,0.5*PI,1.0*PI)
                        }
                        Direction.Down -> {
                            vector3D.rotate(0.5*PI,-0.0*PI,-0.5*PI)
                        }
                    }
                }
                Direction.Right -> {
                    when(metalButtonDirection){
                        Direction.TowardsBody -> {
                            vector3D.rotate(0.0,-1.5*PI,0.0)
                        }
                        Direction.Up -> {
                            vector3D.rotate(-0.5*PI,-0.0*PI,-0.5*PI)
                        }
                        Direction.AwayBody -> {
                            vector3D.rotate(0.0*PI,1.5*PI,1.0*PI)
                        }
                        Direction.Down -> {
                            vector3D.rotate(0.5*PI,-0.0*PI,0.5*PI)
                        }
                    }
                }
                Direction.TowardsBody -> {
                    when(metalButtonDirection){
                        Direction.Up -> {
                            vector3D.rotate(-0.5*PI,0.0,1.0*PI)
                        }
                        Direction.Left -> {
                            vector3D.rotate(0.0,0.5*PI,-0.5* PI)
                        }
                        Direction.Down -> {
                            vector3D.rotate(0.5*PI,0.0,0.0*PI)
                        }
                        Direction.Right -> {
                            vector3D.rotate(0.0,-0.5*PI,0.5* PI)
                        }
                    }
                }
                Direction.AwayBody -> {
                    when(metalButtonDirection){
                        Direction.Up -> {
                            vector3D.rotate(-0.5*PI,0.0* PI,0.0*PI)
                        }
                        Direction.Left -> {
                            vector3D.rotate(-0.5*PI,-0.5*PI,0.0* PI)
                        }
                        Direction.Down -> {
                            vector3D.rotate(0.5*PI,0.0,1* PI)
                        }
                        Direction.Right -> {
                            vector3D.rotate(0.5*PI,0.5*PI,-1.0* PI)
                        }
                    }
                }
            }

        }

        @Composable
        fun changeOrientationSetting(){
            var expandedMetal by remember { mutableStateOf(false) }
            var expandedSensor by remember { mutableStateOf(false) }

            Column {
                //Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.TopStart)) {
                    Button(onClick = { expandedMetal = true }) {
                        Text("MetalButton Direction: $metalButtonDirection")
                    }
                    DropdownMenu(
                        expanded = expandedMetal,
                        onDismissRequest = { expandedMetal = false }
                    ) {
                        DropdownMenuItem(onClick = { metalButtonDirection = Direction.Up }) {
                            Text("Up")
                        }
                        DropdownMenuItem(onClick = { metalButtonDirection = Direction.Down }) {
                            Text("Down")
                        }
                        DropdownMenuItem(onClick = { metalButtonDirection = Direction.Left }) {
                            Text("Left")
                        }
                        DropdownMenuItem(onClick = { metalButtonDirection = Direction.Right }) {
                            Text("Right")
                        }
                        DropdownMenuItem(onClick = { metalButtonDirection = Direction.TowardsBody }) {
                            Text("Towards Body")
                        }
                        DropdownMenuItem(onClick = { metalButtonDirection = Direction.AwayBody }) {
                            Text("Away Body")
                        }
                    }
                //}
                //Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.TopStart)) {
                    Button(onClick = { expandedSensor = true }) {
                        Text("Sensor Direction $sensorDirection")
                    }
                    DropdownMenu(
                        expanded = expandedSensor,
                        onDismissRequest = { expandedSensor = false }
                    ) {
                        DropdownMenuItem(onClick = { sensorDirection = Direction.Up }) {
                            Text("Up")
                        }
                        DropdownMenuItem(onClick = { sensorDirection = Direction.Down }) {
                            Text("Down")
                        }
                        DropdownMenuItem(onClick = { sensorDirection = Direction.Left }) {
                            Text("Left")
                        }
                        DropdownMenuItem(onClick = { sensorDirection = Direction.Right }) {
                            Text("Right")
                        }
                        DropdownMenuItem(onClick = { sensorDirection = Direction.TowardsBody }) {
                            Text("Towards Body")
                        }
                        DropdownMenuItem(onClick = { sensorDirection = Direction.AwayBody }) {
                            Text("Away Body")
                        }
                    }
                //}
            }

        }
    }
    // lights up -> up = swimmer right -> +z right = +x
    private fun checkInputDirection(direction: MutableStateFlow<Boolean>, input: Double, sens: Int,timeStamp:Long)
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
                suspendInputUntill = timeStamp+inputCooldownTime
                Log.d(TAG_INPUT,"Suspend untill $suspendInputUntill")

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