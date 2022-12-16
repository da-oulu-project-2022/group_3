package com.example.polarmove

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.input.TextFieldValue
import com.example.firscomposeapp.PolarController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList


@Composable
fun GameScreen(
    userVM: UserVM,
    gameVM: GameVM,
    gameState: GameState,
    walkCycle: ArrayList<ImageLoader.ImageWithName>,
    jumpCycle: ArrayList<ImageLoader.ImageWithName>,
    crawlCycle: ArrayList<ImageLoader.ImageWithName>,
    roadObjects: ArrayList<ImageLoader.ImageWithName>,
    backgroundObjects: ArrayList<ImageLoader.ImageWithName>,
    cloudItems: ArrayList<ImageLoader.ImageWithName>,
    greeneryObjects: ArrayList<ImageLoader.ImageWithName>,
    manholeItem: ArrayList<ImageBitmap>,
    obstXposs: List<Int>,
    controller: PolarController
){


//    val TAG = "MY-TAG"

    val deviceId = "B5DED921"

    val obstacleState by remember { mutableStateOf( ObstacleState( roadObjects = roadObjects, obstXposs = obstXposs ) ) }
    val bgItemsState by remember { mutableStateOf( BgItemsState( backgroundObjects = backgroundObjects ) ) }
    val bgGreeneryItemsState by remember { mutableStateOf( BgGreeneryItemsState ( greeneryObjects = greeneryObjects ) ) }
    val cloudState by remember { mutableStateOf( CloudState( cloudItems = cloudItems ) ) }
    val roadState by remember { mutableStateOf( RoadState() ) }
    val playerState by remember { mutableStateOf( PlayerState( walkCycle = walkCycle, crawlCycle = crawlCycle, jumpCycle = jumpCycle, gameVM = gameVM ) ) }
    val manholeState by remember { mutableStateOf( ManholeState( manholeItem = manholeItem ) ) }
    val currentScore by gameState.currentScore.observeAsState()
    val highScore by gameState.highScore.observeAsState()

    LaunchedEffect( playerState  ) {
        GlobalScope.launch {
            controller.inputLeft.collect{
                if(it) {
                    highScore?.let { it1 -> playerState.moveLeft(it1) }
                }
            }
        }
        GlobalScope.launch {
            controller.inputRight.collect{
                if(it) {
                    highScore?.let { it1 -> playerState.moveRight(it1) }
                }
            }
        }
        GlobalScope.launch {
            controller.inputUp.collect{
                if(it) {
                    currentScore?.let { it1 -> playerState.up(it1) }
                }
            }
        }
        GlobalScope.launch {
            controller.inputDown.collect{
                if(it) {
                    currentScore?.let { it1 -> playerState.crawl(it1) }
                }
            }
        }
    }

    if ( !gameState.isGameOver ) {
        gameState.increaseScore()
        obstacleState.moveDown()
        bgItemsState.moveDown()
//        manholeState.moveDown()
        bgGreeneryItemsState.moveDown()
        cloudState.moveRight()
        playerState.move()

        obstacleState.obstacleList.forEach { obstacle ->
            if ( playerState.getBounds()
                    .deflate(90f)
                    .overlaps( obstacle.getBounds() ) && playerState.zLevel >= obstacle.zLevel
            ) {
                gameState.isGameOver = true
//                gameVM.setTimePlayed(currentScore?.div(100))
//                gameVM.saveGame(110, 145, userVM.userEmail.value, userVM.userName.value, userVM.userData.weight.toInt(), userVM.userData.age.toInt(), userVM.userData.gender )
                return@forEach
            }
        }

    }

    Row(
        modifier = Modifier
            .fillMaxSize()
    ){

        Column(
            modifier = Modifier
                .width((0.15 * deviceWidthInPixels).dp)
                .fillMaxHeight()
                .background(color = Color.White),
            verticalArrangement = Arrangement.SpaceBetween
        ){

            Canvas(
                modifier = Modifier
//                    .fillMaxWidth()
                    .height((0.1 * deviceHeightInPixels).dp)
            ) {
                greeneryItemView(bgGreeneryItemsState)
                backgroundItemView(bgItemsState)
            }

            Column(
                modifier = Modifier
                    .background(Color.Black)
                    .padding(10.dp, 0.dp)
            ){
                val textState = remember { mutableStateOf(TextFieldValue()) }
                val connectionState by controller.connectionStateText
                TextField(
                    value = textState.value,
                    onValueChange = { textState.value = it }
                )
                Text("connect to Polar device ID: " + textState.value.text)
                OutlinedButton(onClick = { controller.connectToDevice(textState.value.text)} ) {
                    Text(text = connectionState)
                }

                OutlinedButton(onClick = { controller.startStream() }) {
                    Text("Show acc")
                }

              controller.showOrientationSettings()
                OutlinedButton(onClick = {
                    currentScore?.let { playerState.moveLeft(it) }
                }) {
                    Text("Left")
                }
                OutlinedButton(onClick = {
                    currentScore?.let { playerState.moveRight(it) }
                }) {
                    Text("Right")
                }
                OutlinedButton(onClick = {
                    currentScore?.let { playerState.up(it) }
                }) {
                    Text("Up")
                }
                OutlinedButton(onClick = {
                    currentScore?.let { playerState.crawl(it) }
                }) {
                    Text("Down")
                }
                Text("Dashes: ${gameVM.dashes.value}")
                Text("Jumps: ${gameVM.jumps.value}")
                Text("Squats: ${gameVM.squats.value}")
                HighScoreTextViews(requireNotNull(currentScore), requireNotNull(highScore), userVM )
                Text("Score: ${gameVM.score.value}")
                Text("HR: ${gameVM.hr.value}")
                Text("Battery: ${gameVM.batteryLevel.value}%")
            }
        }

        Column(
            modifier = Modifier
                .width((0.6 * deviceWidthInPixels).dp)
                .fillMaxHeight()
//            .background( color = Color.White)
                .clickable(
                    onClick = {
                        if (!gameState.isGameOver) {
                            currentScore?.let { playerState.moveRight(it) }
                        } else {
                            roadState.initLane()
                            obstacleState.initObstacle()
                            playerState.playerInit()
                            gameState.replay()
                        }
                    }
                )
        ) {
            Canvas( modifier = Modifier ){
                roadView( roadState )
                roadBricks( obstacleState )
                obstacleView( obstacleState )
                playerView( playerState )
                cloudView(cloudState)
            }
        }
    }
}

fun DrawScope.obstacleView( obstacleState: ObstacleState ) {
    obstacleState.obstacleList.forEach { obstacle ->
        withTransform({
            translate( left = obstacle.xPos.toFloat(), top = obstacle.yPos.toFloat() )
        }) {
            drawImage( obstacle.image )
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
    drawImage(
        playerState.image,
        topLeft = Offset(x = playerState.xPos.toFloat(), y = playerState.yPos.toFloat())
    )
}

fun DrawScope.manholeView( manholeState: ManholeState ) {
    manholeState.manholeList.forEach { manhole ->
        withTransform({
            translate( left = manhole.xPos.toFloat(), top = manhole.yPos.toFloat() )
        }) {}
    }
}

fun DrawScope.backgroundItemView( bgItemsState: BgItemsState ) {
    bgItemsState.bgItemList.forEach { background ->
        withTransform({
            translate( left = background.xPos.toFloat(), top = background.yPos.toFloat() )
        }) {
            drawImage( background.image )
        }
    }
}

fun DrawScope.greeneryItemView( greeneryItemsState: BgGreeneryItemsState ) {
    greeneryItemsState.bgGreeneryItemList.forEach { greenery ->
        withTransform({
            translate( left = greenery.xPos.toFloat(), top = greenery.yPos.toFloat() )
        }) {
            drawImage( greenery.image )
        }
    }
}

fun DrawScope.cloudView( cloudState: CloudState ) {
    cloudState.cloudList.forEach { clouds ->
        withTransform({
            translate( left = clouds.xPos.toFloat(), top = clouds.yPos.toFloat() )
        }) {
            drawImage( clouds.image )
        }
    }
}


fun DrawScope.roadBricks( obstacleState: ObstacleState ) {
    obstacleState.obstacleList.forEach { obstacle ->
        // Brick work
        drawLine(
            color = Color.Black,
            start = Offset(
                x = (deviceWidthInPixels * lineStart).toFloat(),
                y = obstacle.yPos.toFloat() - 200
            ),
            end = Offset(
                x = (deviceWidthInPixels / lineEnd).toFloat(),
                y = obstacle.yPos.toFloat() - 200
            ),
            strokeWidth = 10f
        )

//        drawLine(
//            color = Color.Black,
//            start = Offset(
//                x = (deviceWidthInPixels.toFloat() * -0.14).toFloat(),
//                y = obstacle.yPos.toFloat() + 2000
//            ),
//            end = Offset(
//                x = (deviceWidthInPixels.toFloat() / 2.41).toFloat(),
//                y = obstacle.yPos.toFloat() + 2000
//            ),
//            strokeWidth = 10f
//        )
    }
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
fun HighScoreTextViews(currentScore: Int, highScore: Int, userVM: UserVM) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Black)
//            .padding( 10.dp, 0.dp )
    ) {
        Text( text = userVM.userData.username )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Time: ${currentScore / 100}", color = Color.White
        )
    }
}