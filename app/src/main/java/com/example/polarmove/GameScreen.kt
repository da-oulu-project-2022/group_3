package com.example.polarmove

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.polar.sdk.api.PolarBleApi
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import com.polar.sdk.api.PolarBleApiCallback
import com.polar.sdk.api.model.PolarDeviceInfo
import com.polar.sdk.api.model.PolarHrData
import io.reactivex.rxjava3.disposables.Disposable
import java.util.*
import kotlin.collections.ArrayList


@Composable
fun GameScreen(
    navControl: NavController,
    userVM: UserVM,
    gameVM: GameVM,
    api: PolarBleApi,
    height: Int,
    width: Int,
    gameState: GameState,
    walkCycle: ArrayList<ImageBitmap>,
    jumpCycle: ArrayList<ImageBitmap>,
    crawlCycle: ArrayList<ImageBitmap>,
    roadObjects: ArrayList<ImageBitmap>,
    obstXposs: List<Int>
){

    val TAG = "MY-TAG"
//
    var accDisposable: Disposable? = null
    var autoConnectDisposable: Disposable? = null
//    var deviceId = "B5DED921"
//
//    var bluetoothEnabled = false
//    var deviceConnected = false
//
//
//
//    api.setApiCallback(object : PolarBleApiCallback() {
//        override fun blePowerStateChanged(powered: Boolean) {
//            Log.d(TAG, "BLE power: $powered")
//            bluetoothEnabled = powered
//            if (powered) {
////                        enableAllButtons()
////                showToast("Phone Bluetooth on")
//            } else {
////                        disableAllButtons()
////                showToast("Phone Bluetooth off")
//            }
//        }
//
//        override fun deviceConnected(polarDeviceInfo: PolarDeviceInfo) {
//            Log.d(TAG, "CONNECTED: " + polarDeviceInfo.deviceId)
//            deviceId = polarDeviceInfo.deviceId
//            deviceConnected = true
////                    val buttonText = getString(R.string.disconnect_from_device, deviceId)
////                    toggleButtonDown(connectButton, buttonText)
//        }
//
//        override fun deviceConnecting(polarDeviceInfo: PolarDeviceInfo) {
//            Log.d(TAG, "CONNECTING: " + polarDeviceInfo.deviceId)
//        }
//
//        override fun deviceDisconnected(polarDeviceInfo: PolarDeviceInfo) {
//            Log.d(TAG, "DISCONNECTED: " + polarDeviceInfo.deviceId)
//            deviceConnected = false
////                    val buttonText = getString(R.string.connect_to_device, deviceId)
////                    toggleButtonUp(connectButton, buttonText)
////                    toggleButtonUp(toggleSdkModeButton, R.string.enable_sdk_mode)
//        }
//
//        override fun streamingFeaturesReady(
//            identifier: String, features: Set<PolarBleApi.DeviceStreamingFeature>
//        ) {
//            for (feature in features) {
//                Log.d(TAG, "Streaming feature $feature is ready")
//            }
//        }
//
//        override fun hrFeatureReady(identifier: String) {
//            Log.d(TAG, "HR READY: $identifier")
//            // hr notifications are about to start
//        }
//
//        override fun disInformationReceived(identifier: String, uuid: UUID, value: String) {
//            Log.d(TAG, "uuid: $uuid value: $value")
//        }
//
//        override fun batteryLevelReceived(identifier: String, level: Int) {
//            Log.d(TAG, "BATTERY LEVEL: $level")
//        }
//
//        override fun hrNotificationReceived(identifier: String, data: PolarHrData) {
//            Log.d(TAG, "HR value: ${data.hr} rrsMs: ${data.rrsMs} rr: ${data.rrs} contact: ${data.contactStatus} , ${data.contactStatusSupported}")
////            hr.value = data.hr
//            gameVM.setHr(data.hr)
//        }
//
//
//        override fun polarFtpFeatureReady(s: String) {
//            Log.d(TAG, "FTP ready")
//        }
//    })

//
//    var playerX by remember { mutableStateOf(967)}
//    val playerY = 2650
//
//    fun moveLeft(){
//        if( playerX >= 967 ) playerX -= 260
//    }
//    fun moveRight(){
//        if ( playerX <= 967 ) playerX += 260
//    }
//
//    val xPossibilities = listOf( 707, 967, 1227 )
//
//    Column(
//        modifier = Modifier.fillMaxSize()
//    ){
//        OutlinedButton(onClick = { navControl.navigate("MainScreen") }) {
//            Text(text = "Main screen")
//        }
//
//        OutlinedButton(onClick = {
//            if (autoConnectDisposable != null) {
//                autoConnectDisposable?.dispose()
//            }
//            autoConnectDisposable = api.autoConnectToDevice(-60, "180D", null)
//                .subscribe(
//                    { Log.d(TAG, "auto connect search complete") },
//                    { throwable: Throwable -> Log.e(TAG, "" + throwable.toString()) }
//                )
//        }) {
//            Text(text = "Connect Sensor")
//        }
//
//        OutlinedButton(onClick = {
//            if (accDisposable == null) {
//                accDisposable = api.requestStreamSettings(deviceId, PolarBleApi.DeviceStreamingFeature.ACC)
//                    .toFlowable()
//                    .flatMap { sensorSetting: PolarSensorSetting -> api.startAccStreaming(deviceId, sensorSetting.maxSettings()) }
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(
//                        { polarAccData: PolarAccelerometerData ->
//                            Log.d(TAG, "ACC update")
//                            for (data in polarAccData.samples) {
//
//                                Log.d("XXX ", data.x.toString())
//                                Log.d("YYY ", data.y.toString())
//                                if ( data.z < -200) {
//                                    moveRight()
//                                } else if ( data.z > 200 ){
//                                    moveLeft()
//                                }
//                            }
//                        },
//                        { error: Throwable ->
//                            Log.e(TAG, "Ecg stream failed $error")
//                            accDisposable = null
//                        },
//                        {
//                            Log.d(TAG, "Ecg stream complete")
//                        }
//                    )
//            } else {
//                // NOTE stops streaming if it is "running"
//                accDisposable?.dispose()
//                accDisposable = null
//            }
//        }) {
//            Text(text = "Show acceleration")
//        }
//
//        Text("HR: $hr")
////        Text(horizontal)
////        Text(vertical)
//
//        OutlinedButton(onClick = {
////            Log.d("XXX ", listX.toString())
////            Log.d("YYY ", listY.toString())
//            accDisposable?.dispose()
//        }) {
//            Text(text = "Stop")
//        }
//
//        OutlinedButton(onClick = {
//            if ( playerX <= 967 ) playerX += 260
//        }) {
//            Text(text = "Move right")
//        }
//        OutlinedButton(onClick = {
//            if( playerX >= 967 ) playerX -= 260
//        }) {
//            Text(text = "Move left")
//        }
//
//    }

    /////////////////////////////////////////////////////////////////////////////////

//    val option = BitmapFactory.Options()
//    option.inPreferredConfig = Bitmap.Config.ARGB_8888

//    var walk1: ImageBitmap
//    var walk2: ImageBitmap
//    var imageBoolean by remember { mutableStateOf(false)}
//
//
//    if ( deviceWidthInPixels > 1080 ) {
//        walk1 = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catwalkcycle1 ).asImageBitmap()
//        walk2 = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catwalkcycle2 ).asImageBitmap()
//
//        Log.d("COUNTER", "COUUUUUUUUNT")
//    } else {
//        val walk1bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catwalkcycle1 )
//        val walk2bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catwalkcycle2 )
//        walk1 = Bitmap.createScaledBitmap( walk1bitmap, 380, 380, true ).asImageBitmap()
//        walk2 = Bitmap.createScaledBitmap( walk2bitmap, 380, 380, true ).asImageBitmap()
//    }


    val obstacleState by remember { mutableStateOf( ObstacleState( roadObjects = roadObjects, obstXposs = obstXposs) ) }
    val roadState by remember { mutableStateOf( RoadState() ) }
    val playerState by remember { mutableStateOf( PlayerState( walkCycle = walkCycle, jumpCycle = jumpCycle, crawlCycle = crawlCycle ) ) }
    val currentScore by gameState.currentScore.observeAsState()
    val highScore by gameState.highScore.observeAsState()

    if ( !gameState.isGameOver ) {
        gameState.increaseScore()
        obstacleState.moveDown()
        playerState.move()


        obstacleState.obstacleList.forEach { obstacle ->
            if ( playerState.getBounds()
                    .deflate(90f)
                    .overlaps( obstacle.getBounds() ) && playerState.zLevel >= obstacle.zLevel
            ) {
                gameState.isGameOver = true
                return@forEach
            }
        }

    }

    Row(
//        modifier = Modifier
//            .fillMaxSize()
    ){

        Column(
            modifier = Modifier
                .width((0.15 * deviceWidthInPixels).dp)
                .fillMaxHeight()
                .clickable(
                    onClick = {
                        if (!gameState.isGameOver) {
                            playerState.moveRight()
                        } else {
                            roadState.initLane()
                            obstacleState.initObstacle()
                            playerState.playerInit()
                            gameState.replay()
                        }
                    }
                )
//                .fillMaxHeight()
        ){
            Spacer(modifier = Modifier.height(500.dp))
            OutlinedButton(onClick = {
                if (autoConnectDisposable != null) {
                    autoConnectDisposable?.dispose()
                }
                autoConnectDisposable = api.autoConnectToDevice(-60, "180D", null)
                    .subscribe(
                        { Log.d(TAG, "auto connect search complete") },
                        { throwable: Throwable -> Log.e(TAG, "" + throwable.toString()) }
                    )
                }
            ) {
                Text(text = "Connect Sensor")
            }
            OutlinedButton(onClick = {
                playerState.moveLeft()
            }) {
                Text("Left")
            }
            OutlinedButton(onClick = {
                playerState.moveRight()
            }) {
                Text("Right")
            }
            OutlinedButton(onClick = {
                playerState.jump()
            }) {
                Text("Jump")
            }
            OutlinedButton(onClick = {
                playerState.crawl()
            }) {
                Text("Crawl")
            }
            HighScoreTextViews(requireNotNull(currentScore), requireNotNull(highScore))
            Text("HR: ${gameVM.hr.value}")
            Text("Battery: ${gameVM.batteryLevel.value}")
        }

        Column(
            modifier = Modifier
                .width((0.6 * deviceWidthInPixels).dp)
                .fillMaxHeight()
//            .background( color = Color.White)
                .clickable(
                    onClick = {
                        if (!gameState.isGameOver) {
                            playerState.moveRight()
                        } else {
                            roadState.initLane()
                            obstacleState.initObstacle()
                            playerState.playerInit()
                            gameState.replay()
                        }
                    }
                )
        ) {
//        Image(painter = painterResource(id = R.drawable.catwalkcycle), "sdsd", )
//            HighScoreTextViews(requireNotNull(currentScore), requireNotNull(highScore))
            Canvas( modifier = Modifier ){
                roadView( roadState )
                obstacleView( obstacleState )
                playerView( playerState )
//            drawImage( walk1, alpha = 1f, style = Fill, topLeft = Offset( x = playerState.xPos.toFloat(), y = playerState.yPos.toFloat()) )
            }
        }
    }

//    Column(
//        modifier = Modifier
//            .fillMaxSize()
////            .background( color = Color.White)
//            .clickable(
//                onClick = {
//                    if (!gameState.isGameOver) {
//                        playerState.moveRight()
//                    } else {
//                        roadState.initLane()
//                        obstacleState.initObstacle()
//                        playerState.playerInit()
//                        gameState.replay()
//                    }
//                }
//            )
//    ) {
////        Image(painter = painterResource(id = R.drawable.catwalkcycle), "sdsd", )
//        HighScoreTextViews(requireNotNull(currentScore), requireNotNull(highScore))
//        Canvas( modifier = Modifier.fillMaxSize() ){
//            roadView( roadState )
//            obstacleView( obstacleState )
//            playerView( playerState )
////            drawImage( walk1, alpha = 1f, style = Fill, topLeft = Offset( x = playerState.xPos.toFloat(), y = playerState.yPos.toFloat()) )
//        }
//
//
//    }

}

fun DrawScope.obstacleView( obstacleState: ObstacleState ) {
    obstacleState.obstacleList.forEach { obstacle ->
//        withTransform({
////            scale( .8f, .8f)
//            translate( left = obstacle.xPos.toFloat(), top = obstacle.yPos.toFloat() )
//        }) {
////            drawRect( color = Color.Red, size = Size( width = obstacle.size.toFloat(), height = obstacle.size.toFloat() ) )
//            drawBoundingBox( Color.Red, obstacle.getBounds())
//            drawImage( obstacle.image )
//
//        }
        withTransform({
//            scale( .8f, .8f)
            translate( left = obstacle.xPos.toFloat(), top = obstacle.yPos.toFloat() )
        }) {
////            drawRect( color = Color.Red, size = Size( width = obstacle.size.toFloat(), height = obstacle.size.toFloat() ) )
//            drawImage( obstacle.image, topLeft = Offset( x = obstacle.xPos.toFloat(), y = obstacle.yPos.toFloat()) )
            drawImage( obstacle.image )
//            drawBoundingBox( Color.Red, obstacle.getBounds())
        }
    }
}

fun DrawScope.roadView( roadState: RoadState ) {
    roadState.laneList.forEach { lane ->
        drawRect( color = Color.Gray,
            topLeft = Offset( x = lane.xPos.toFloat(), y = lane.yPos.toFloat() ),
            size = Size( width = lane.width.toFloat(), height = lane.height.toFloat()) )
    }
}

fun DrawScope.playerView( playerState: PlayerState ) {
    drawImage( playerState.image, topLeft = Offset( x = playerState.xPos.toFloat(), y = playerState.yPos.toFloat()) )
//    drawBoundingBox( Color.Red, playerState.getBounds())
}

fun DrawScope.drawBoundingBox(color: Color, rect: Rect ) {
        drawRect(color, rect.topLeft, rect.size, style = Stroke(3f))
        drawRect(
            color,
            rect.deflate(90f).topLeft,
            rect.deflate(90f).size,
            style = Stroke(
                width = 13f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(2f, 4f), 0f)
            )
        )
}


@Composable
fun HighScoreTextViews(currentScore: Int, highScore: Int)
{
//    Spacer(modifier = Modifier.padding(top = 50.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(text = "Score")
//        Spacer(modifier = Modifier.padding(start = 10.dp))
//        Text(
//            text = "$highScore".padStart(5, '0')
//        )
        Spacer(modifier = Modifier.padding(start = 10.dp))
        Text(
            text = "$currentScore".padStart(5, '0')
        )
    }
}