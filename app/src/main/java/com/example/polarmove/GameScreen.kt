package com.example.polarmove

import android.graphics.Bitmap
import android.media.Image
import android.util.DisplayMetrics
import android.util.Log
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.polar.sdk.api.PolarBleApi
import com.polar.sdk.api.model.PolarAccelerometerData
import com.polar.sdk.api.model.PolarSensorSetting
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable


@Composable
fun GameScreen( navControl: NavController, userVM: UserVM, gameVM: GameVM, api: PolarBleApi, hr: Int, height: Int, width: Int ){

    val TAG = "MY-TAG"

    var accDisposable: Disposable? = null
    var autoConnectDisposable: Disposable? = null
    val deviceId = "B5DED921"

    var playerX by remember { mutableStateOf(967)}
    val playerY = 2650

    fun moveLeft(){
        if( playerX >= 967 ) playerX -= 260
    }
    fun moveRight(){
        if ( playerX <= 967 ) playerX += 260
    }

    val xPossibilities = listOf( 707, 967, 1227 )

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

                                Log.d("XXX ", data.x.toString())
                                Log.d("YYY ", data.y.toString())
                                if ( data.z < -200) {
                                    moveRight()
                                } else if ( data.z > 200 ){
                                    moveLeft()
                                }
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
//        Text(horizontal)
//        Text(vertical)

        OutlinedButton(onClick = {
//            Log.d("XXX ", listX.toString())
//            Log.d("YYY ", listY.toString())
            accDisposable?.dispose()
        }) {
            Text(text = "Stop")
        }

        OutlinedButton(onClick = {
            if ( playerX <= 967 ) playerX += 260
        }) {
            Text(text = "Move right")
        }
        OutlinedButton(onClick = {
            if( playerX >= 967 ) playerX -= 260
        }) {
            Text(text = "Move left")
        }

    }

}