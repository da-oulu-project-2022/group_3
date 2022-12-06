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

var deviceHeightInPixels = 1920
var deviceWidthInPixels = 1080
var distanceBetweenObstacles = 1000
var obstacleSpeed = 5
const val distanceBetweenLines = 10
//val xPossibilities = listOf( obst1.toInt(), obst2.toInt(), obst3.toInt() )

@Composable
fun GameScreen( navControl: NavController, userVM: UserVM, gameVM: GameVM, api: PolarBleApi, hr: Int, height: Int, width: Int, gameState: GameState ){

//    val TAG = "MY-TAG"
//
//    var accDisposable: Disposable? = null
//    var autoConnectDisposable: Disposable? = null
//    val deviceId = "B5DED921"
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
    val walk1 = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catwalkcycle ).asImageBitmap()
    val walk2 = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catwalkcycle2 ).asImageBitmap()

    val obstacleState by remember { mutableStateOf( ObstacleState() ) }
    val roadState by remember { mutableStateOf( RoadState() ) }
    val playerState by remember { mutableStateOf( PlayerState( walk1 = walk1, walk2 = walk2) ) }
    val currentScore by gameState.currentScore.observeAsState()
    val highScore by gameState.highScore.observeAsState()

    if ( !gameState.isGameOver ) {
        gameState.increaseScore()
        obstacleState.moveDown()
        playerState.run()

        obstacleState.obstacleList.forEach { obstacle ->
            if ( playerState.getBounds()
                    .deflate(110f)
                    .overlaps( obstacle.getBounds() ) && playerState.zLevel >= obstacle.zLevel
            ) {
                gameState.isGameOver = true
                return@forEach
            }
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
        HighScoreTextViews(requireNotNull(currentScore), requireNotNull(highScore))
        Canvas( modifier = Modifier.fillMaxSize() ){
            roadView( roadState )
            obstacleView( obstacleState )
            playerView( playerState )
//            drawImage( walk1, alpha = 1f, style = Fill, topLeft = Offset( x = playerState.xPos.toFloat(), y = playerState.yPos.toFloat()) )
        }


    }

}

fun DrawScope.obstacleView( obstacleState: ObstacleState ) {
    obstacleState.obstacleList.forEach { obstacle ->
        withTransform({
            translate( left = obstacle.xPos.toFloat(), top = obstacle.yPos.toFloat() )
        }) {
            drawRect( color = Color.Red, size = Size( width = obstacle.size.toFloat(), height = obstacle.size.toFloat() ) )
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
    drawBoundingBox( Color.Red, playerState.getBounds())
}

fun DrawScope.drawBoundingBox(color: Color, rect: Rect ) {
        drawRect(color, rect.topLeft, rect.size, style = Stroke(3f))
        drawRect(
            color,
            rect.deflate(110f).topLeft,
            rect.deflate(110f).size,
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