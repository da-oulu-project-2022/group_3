package com.example.polarmove

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.polarmove.ui.theme.PolarMoveTheme
import com.polar.sdk.api.PolarBleApi
import com.polar.sdk.api.PolarBleApiCallback
import com.polar.sdk.api.PolarBleApiDefaultImpl
import com.polar.sdk.api.model.PolarDeviceInfo
import com.polar.sdk.api.model.PolarHrData
import java.util.*


var deviceHeightInPixels = 3060
var deviceWidthInPixels = 1440
var distanceBetweenObstacles = 1000
var obstacleSpeed = 1
const val distanceBetweenLines = 10

class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "MainActivity"
        private const val API_LOGGER_TAG = "API LOGGER"
        private const val PERMISSION_REQUEST_CODE = 1
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(applicationContext, message, Toast.LENGTH_LONG)
        toast.show()

    }

    private var deviceId = "B5DED921"

//    val api: PolarBleApi = PolarBleApiDefaultImpl.defaultImplementation(applicationContext,  PolarBleApi.ALL_FEATURES)

    private val api: PolarBleApi by lazy {
        // Notice PolarBleApi.ALL_FEATURES are enabled
        PolarBleApiDefaultImpl.defaultImplementation(applicationContext, PolarBleApi.ALL_FEATURES)
    }

    private var bluetoothEnabled = false
    private var deviceConnected = false
    var hr = mutableStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            api.setPolarFilter(false)
            api.setApiLogger { s: String -> Log.d(API_LOGGER_TAG, s) }
            api.setApiCallback(object : PolarBleApiCallback() {
                override fun blePowerStateChanged(powered: Boolean) {
                    Log.d(TAG, "BLE power: $powered")
                    bluetoothEnabled = powered
                    if (powered) {
//                        enableAllButtons()
                        showToast("Phone Bluetooth on")
                    } else {
//                        disableAllButtons()
                        showToast("Phone Bluetooth off")
                    }
                }

                override fun deviceConnected(polarDeviceInfo: PolarDeviceInfo) {
                    Log.d(TAG, "CONNECTED: " + polarDeviceInfo.deviceId)
                    deviceId = polarDeviceInfo.deviceId
                    deviceConnected = true
//                    val buttonText = getString(R.string.disconnect_from_device, deviceId)
//                    toggleButtonDown(connectButton, buttonText)
                }

                override fun deviceConnecting(polarDeviceInfo: PolarDeviceInfo) {
                    Log.d(TAG, "CONNECTING: " + polarDeviceInfo.deviceId)
                }

                override fun deviceDisconnected(polarDeviceInfo: PolarDeviceInfo) {
                    Log.d(TAG, "DISCONNECTED: " + polarDeviceInfo.deviceId)
                    deviceConnected = false
//                    val buttonText = getString(R.string.connect_to_device, deviceId)
//                    toggleButtonUp(connectButton, buttonText)
//                    toggleButtonUp(toggleSdkModeButton, R.string.enable_sdk_mode)
                }

                override fun streamingFeaturesReady(
                    identifier: String, features: Set<PolarBleApi.DeviceStreamingFeature>
                ) {
                    for (feature in features) {
                        Log.d(TAG, "Streaming feature $feature is ready")
                    }
                }

                override fun hrFeatureReady(identifier: String) {
                    Log.d(TAG, "HR READY: $identifier")
                    // hr notifications are about to start
                }

                override fun disInformationReceived(identifier: String, uuid: UUID, value: String) {
                    Log.d(TAG, "uuid: $uuid value: $value")
                }

                override fun batteryLevelReceived(identifier: String, level: Int) {
                    Log.d(TAG, "BATTERY LEVEL: $level")
                }

                override fun hrNotificationReceived(identifier: String, data: PolarHrData) {
                    Log.d(TAG, "HR value: ${data.hr} rrsMs: ${data.rrsMs} rr: ${data.rrs} contact: ${data.contactStatus} , ${data.contactStatusSupported}")
                    hr.value = data.hr
                }


                override fun polarFtpFeatureReady(s: String) {
                    Log.d(TAG, "FTP ready")
                }
            })

            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val height = displayMetrics.heightPixels
            val width = displayMetrics.widthPixels

            deviceHeightInPixels = displayMetrics.heightPixels
            deviceWidthInPixels = displayMetrics.widthPixels

            PolarMoveTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    StartPoint( api, hr.value, height, width )
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                requestPermissions(arrayOf(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT), PERMISSION_REQUEST_CODE)
            } else {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
            }
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_REQUEST_CODE)
        }
    }
}