package com.example.polarmove

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.graphics.drawscope.Stroke


@Composable
fun GameScreen(
    navControl: NavController,
    userVM: UserVM,
    gameVM: GameVM,
    api: PolarBleApi,
    hr: Int, height: Int,
    width: Int,
    gameState: GameState,
    walkCycle: ArrayList<ImageBitmap>,
    roadObjects: ArrayList<ImageBitmap>,
    backgroundObjects: ArrayList<ImageBitmap>,
    cloudItems: ArrayList<ImageBitmap>,
    greeneryObjects: ArrayList<ImageBitmap>,
    manholeItem: ArrayList<ImageBitmap>
){


    val obstacleState by remember { mutableStateOf( ObstacleState( roadObjects = roadObjects) ) }
    val bgItemsState by remember { mutableStateOf( BgItemsState( backgroundObjects = backgroundObjects ) ) }
    val bgGreeneryItemsState by remember { mutableStateOf( BgGreeneryItemsState ( greeneryObjects = greeneryObjects ) ) }
    val cloudState by remember { mutableStateOf( CloudState( cloudItems = cloudItems ) ) }
    val roadState by remember { mutableStateOf( RoadState() ) }
    val playerState by remember { mutableStateOf( PlayerState( walkCycle = walkCycle ) ) }
    val manholeState by remember { mutableStateOf( ManholeState( manholeItem = manholeItem ) ) }
    val currentScore by gameState.currentScore.observeAsState()
    val highScore by gameState.highScore.observeAsState()


    if ( !gameState.isGameOver ) {
        gameState.increaseScore()
        obstacleState.moveDown()
        bgItemsState.moveDown()
        manholeState.moveDown()
        bgGreeneryItemsState.moveDown()
        cloudState.moveDown()
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
         .background( color = Color.White)
            .clickable(
                onClick = {
                    if (!gameState.isGameOver) {
                        playerState.moveRight()
   //                   playerState.moveLeft()
                    } else {
                        roadState.initLane()
                        obstacleState.initObstacle()
                        manholeState.initManhole()
                        bgItemsState.initBgItems()
                        bgGreeneryItemsState.initBgGreeneryItems()
                        playerState.playerInit()
                        gameState.replay()
                    }
                }
            )
    ) {
//        Image(painter = painterResource(id = R.drawable.catwalkcycle), "sdsd", )
        HighScoreTextViews(requireNotNull(currentScore), requireNotNull(highScore), userVM)
        Canvas( modifier = Modifier.fillMaxSize() ){
            roadView( roadState )
            roadBricks( obstacleState )
            obstacleView( obstacleState )
            manholeView( manholeState )
            greeneryItemView( bgGreeneryItemsState )
            backgroundItemView( bgItemsState )
            greeneryItemView( bgGreeneryItemsState )
            cloudView( cloudState )
            playerView( playerState )
//            drawImage( walk1, alpha = 1f, style = Fill, topLeft = Offset( x = playerState.xPos.toFloat(), y = playerState.yPos.toFloat()) )
        }


    }

}

fun DrawScope.obstacleView( obstacleState: ObstacleState ) {
    obstacleState.obstacleList.forEach { obstacle ->
        withTransform({
//            scale( .8f, .8f)
            translate( left = obstacle.xPos.toFloat(), top = obstacle.yPos.toFloat() )
        }) {
//            drawRect( color = Color.Red, size = Size( width = obstacle.size.toFloat(), height = obstacle.size.toFloat() ) )
            drawImage( obstacle.image )
            drawBoundingBox( Color.Red, obstacle.getBounds())
        }
    }
}

fun DrawScope.manholeView( manholeState: ManholeState ) {
    manholeState.manholeList.forEach { manhole ->
        withTransform({
//            scale( .8f, .8f)
            translate( left = manhole.xPos.toFloat(), top = manhole.yPos.toFloat() )
        }) {}
    }
}

fun DrawScope.backgroundItemView( bgItemsState: BgItemsState ) {
    bgItemsState.bgItemList.forEach { background ->
        withTransform({
//            scale( .8f, .8f)
            translate( left = background.xPos.toFloat(), top = background.yPos.toFloat() )
        }) {
//            drawRect( color = Color.Red, size = Size( width = obstacle.size.toFloat(), height = obstacle.size.toFloat() ) )
            drawImage( background.image )
        }
    }
}
fun DrawScope.greeneryItemView( greeneryItemsState: BgGreeneryItemsState ) {
    greeneryItemsState.bgGreeneryItemList.forEach { greenery ->
        withTransform({
//            scale( .8f, .8f)
            translate( left = greenery.xPos.toFloat(), top = greenery.yPos.toFloat() )
        }) {
//            drawRect( color = Color.Red, size = Size( width = obstacle.size.toFloat(), height = obstacle.size.toFloat() ) )
            drawImage( greenery.image )
        }
    }
}

fun DrawScope.cloudView( cloudState: CloudState ) {
    cloudState.cloudList.forEach { clouds ->
        withTransform({
//            scale( .8f, .8f)
            translate( left = clouds.xPos.toFloat(), top = clouds.yPos.toFloat() )
        }) {
//            drawRect( color = Color.Red, size = Size( width = obstacle.size.toFloat(), height = obstacle.size.toFloat() ) )
            drawImage( clouds.image )
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
fun DrawScope.roadBricks( obstacleState: ObstacleState ) {
    obstacleState.obstacleList.forEach { obstacle ->
            // Brick work
            drawLine(
                color = Color.LightGray,
                start = Offset(
                    x = (deviceWidthInPixels.toFloat() * 0.99).toFloat(),
                    y = obstacle.yPos.toFloat() + 2000
                ),
                end = Offset(
                    x = (deviceWidthInPixels.toFloat() / 2.27).toFloat(),
                    y = obstacle.yPos.toFloat() + 2000
                ),
                strokeWidth = 10f
            )

            drawLine(
                color = Color.LightGray,
                start = Offset(
                    x = (deviceWidthInPixels.toFloat() * 0.99).toFloat(),
                    y = obstacle.yPos.toFloat() - 2000
                ),
                end = Offset(
                    x = (deviceWidthInPixels.toFloat() / 2.27).toFloat(),
                    y = obstacle.yPos.toFloat() - 2000
                ),
                strokeWidth = 10f
            )
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
fun HighScoreTextViews(currentScore: Int, highScore: Int, userVM: UserVM)
{
//    Spacer(modifier = Modifier.padding(top = 50.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth().background(color = Color.Black)
            .padding(start = 20.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(text = "Username: ${userVM.userData.username}")

        Spacer(modifier = Modifier.padding(start = 10.dp))

        Text(text = "Score:")
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