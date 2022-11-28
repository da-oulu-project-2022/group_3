package com.example.polarmove

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.polar.sdk.api.PolarBleApi
import com.polar.sdk.api.model.PolarAccelerometerData
import com.polar.sdk.api.model.PolarSensorSetting
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable


@Composable
fun GameScreen( navControl: NavController, userVM: UserVM, gameVM: GameVM, api: PolarBleApi, hr: Int ){

    val TAG = "MY-TAG"

    var accDisposable: Disposable? = null
    var autoConnectDisposable: Disposable? = null
    val deviceId = "B5DED921"
    var listX = mutableListOf<Int>()
    var listY = mutableListOf<Int>()

    var horizontal by remember { mutableStateOf("")}
    var vertical by remember { mutableStateOf("")}
//    var hr by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        OutlinedButton(onClick = { navControl.navigate("MainScreen") }) {
            Text(text = "Main screen")
        }

        OutlinedButton(onClick = {
            if (autoConnectDisposable != null) {
                autoConnectDisposable?.dispose()
            }
            autoConnectDisposable = api.autoConnectToDevice(-60, "180D", null)
                .subscribe(
                    { Log.d(TAG, "auto connect search complete") },
                    { throwable: Throwable -> Log.e(TAG, "" + throwable.toString()) }
                )
        }) {
            Text(text = "Connect Sensor")
        }

        OutlinedButton(onClick = {
            if (accDisposable == null) {
                accDisposable = api.requestStreamSettings(deviceId, PolarBleApi.DeviceStreamingFeature.ACC)
                    .toFlowable()
                    .flatMap { sensorSetting: PolarSensorSetting -> api.startAccStreaming(deviceId, sensorSetting.maxSettings()) }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { polarAccData: PolarAccelerometerData ->
                            Log.d(TAG, "ACC update")
                            for (data in polarAccData.samples) {
                                listX.add(data.x)
                                listY.add(data.y)
                                Log.d("XXX ", data.x.toString())
                                Log.d("YYY ", data.y.toString())
                                if ( data.z < -200) {
                                    horizontal = "RIGHT"
                                } else if ( data.z > 200 ){
                                    horizontal = "LEFT"
                                }
                                if ( data.y < -1250 ){
                                    vertical = "DOWN"
                                } else if ( data.y > -750 ) {
                                    vertical = "UP"
                                }
//                                val listData = listOf(polarAccData.samples)
//                                list4e = listData
                            }


                        },
                        { error: Throwable ->
                            Log.e(TAG, "Ecg stream failed $error")
                            accDisposable = null
                        },
                        {
                            Log.d(TAG, "Ecg stream complete")
                        }
                    )
            } else {
                // NOTE stops streaming if it is "running"
                accDisposable?.dispose()
                accDisposable = null
            }
        }) {
            Text(text = "Show acceleration")
        }

        Text("HR: $hr")
        Text(horizontal)
        Text(vertical)

        OutlinedButton(onClick = {
            Log.d("XXX ", listX.toString())
            Log.d("YYY ", listY.toString())
            accDisposable?.dispose()
        }) {
            Text(text = "Stop")
        }

    }

}