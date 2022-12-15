package com.example.polarmove

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firscomposeapp.PolarController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.polar.sdk.api.PolarBleApi



@Composable
fun StartPoint( gameVM: GameVM, controller: PolarController ){

    Log.d("START", "POINT")

    val userVM: UserVM = viewModel()

    val auth = FirebaseAuth.getInstance()
    val user: FirebaseUser? = auth.currentUser

    val context = LocalContext.current

    val catWalkLoader = ImageLoader(context)
    val catJumpLoader = ImageLoader(context)
    val catCrawlLoader = ImageLoader(context)
    val obstacleLoader = ImageLoader(context)
    val backgroundObjectsLoader = ImageLoader(context)
    val greeneryLoader = ImageLoader(context)
    val cloudsLoader = ImageLoader(context)

    //Fetching user data and set user if Firebase user is logged in
    if( user != null ){
        userVM.setEmail( user.email.toString() )
        userVM.fetchUserData()
        gameVM.getOwnGames( user.email.toString() )
        gameVM.getHighScores()
        userVM.setUser( user )
    }

    var obstacleSize = 200
    var playerSize = 250
    var bgItemSize = 700
    var bgItemSize2 = 900
    var bgItemSize3 = 500
    var bgItemSize4 = 400
    var bgItemSize5 = 300
    var cloudSize = 700
    var cloudSize2 = 500
    var plantsSize = 300
    var plantsSize3 = 200
    var plantsSize2 = 500
    var manholeSize = 480
    var manholeSize2 = 280

    if ( deviceWidthInPixels > 1080 ) {
        obstacleSize = 250
        playerSize = 300
        bgItemSize = 800
        bgItemSize2 = 1000
        bgItemSize3 = 600
        bgItemSize4 = 500
        bgItemSize5 = 400
        cloudSize = 800
        cloudSize2 = 600
        plantsSize3 = 300
        plantsSize = 400
        plantsSize2 = 600
        lineStart = -0.14
        lineEnd = 2.41
        manholeSize = 580
        manholeSize2 = 380
    


    //   Manhole
    val manhole: ImageBitmap
    val manhole2: ImageBitmap
    val manholeItem: ArrayList<ImageBitmap> = ArrayList()

//////////// Walk Cycle //////////////////////////////////////////////////////////////////////////////////////

    catWalkLoader.addImage("walk1", playerSize, R.drawable.catwalkcycle1 )
    catWalkLoader.addImage("walk2", playerSize, R.drawable.catwalkcycle2)

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////   Jump Cycle     ///////////////////////////////////////////////////////////////////////////////////

    catJumpLoader.addImage("jump1", playerSize, R.drawable.catjumpcycle1 )
    catJumpLoader.addImage("jump2", playerSize, R.drawable.catjumpcycle2)

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////// Crawl Cycle   /////////////////////////////////////////////////////////////////////////////////////////

    catCrawlLoader.addImage("crawl1", playerSize, R.drawable.catcrouchcycle1)
    catCrawlLoader.addImage("crawl2", playerSize, R.drawable.catcrouchcycle2)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////  Obstacle bitmaps  ////////////////////////////////////////////////////////////////////////////////////////

    obstacleLoader.addImage("grandma", obstacleSize, R.drawable.grandma)
    obstacleLoader.addImage("trashcan", obstacleSize, R.drawable.trashcan)
    obstacleLoader.addImage("trashcan2", obstacleSize, R.drawable.trashcan2)
    obstacleLoader.addImage("roadblock", obstacleSize, R.drawable.roadblock)
    obstacleLoader.addImage("bench", obstacleSize, R.drawable.bench)
    obstacleLoader.addImage("benchCat", obstacleSize, R.drawable.bench_cat)


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//////////////    Background objects    ////////////////////////////////////////////////////////////////////////////////

    backgroundObjectsLoader.addImage("house", bgItemSize, R.drawable.house1version1)
    backgroundObjectsLoader.addImage( "house2", bgItemSize2, R.drawable.house1version2)
    backgroundObjectsLoader.addImage("house3", bgItemSize2, R.drawable.house1version3)
    backgroundObjectsLoader.addImage("house4", bgItemSize, R.drawable.house2version1)
    backgroundObjectsLoader.addImage("house5", bgItemSize, R.drawable.house2version2)
    backgroundObjectsLoader.addImage("house6", bgItemSize, R.drawable.house2version3)
    backgroundObjectsLoader.addImage("bush", plantsSize, R.drawable.bush)
    backgroundObjectsLoader.addImage("bush2", plantsSize2, R.drawable.bush2)
    backgroundObjectsLoader.addImage("bush3", plantsSize, R.drawable.bush3)
    backgroundObjectsLoader.addImage("tree1", bgItemSize4, R.drawable.tree1)
    backgroundObjectsLoader.addImage("tree2", plantsSize2, R.drawable.tree2)
    backgroundObjectsLoader.addImage("flower", plantsSize3, R.drawable.flower)
    backgroundObjectsLoader.addImage("greenery", plantsSize3, R.drawable.greenery)
    backgroundObjectsLoader.addImage("streetlight", bgItemSize, R.drawable.streetlight)
    backgroundObjectsLoader.addImage("trashcan", bgItemSize5, R.drawable.trashcan)


    /////// Greenery objects   ////////////////////////////////////////////////////////////////////////////

    greeneryLoader.addImage("bush", plantsSize, R.drawable.bush)
    greeneryLoader.addImage("bush2", plantsSize, R.drawable.bush2)
    greeneryLoader.addImage("bush3", plantsSize, R.drawable.bush3)
    greeneryLoader.addImage("tree", bgItemSize3, R.drawable.tree1)
    greeneryLoader.addImage("tree2", plantsSize2, R.drawable.tree2)
    greeneryLoader.addImage("flower", plantsSize3, R.drawable.flower)
    greeneryLoader.addImage("greenery", plantsSize2, R.drawable.greenery)


////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/////////    Clouds    ////////////////////////////////////////////////////////////////////////////////////////

    cloudsLoader.addImage("cloud1", cloudSize, R.drawable.cloud)
    cloudsLoader.addImage("cloud2", cloudSize2, R.drawable.cloud2ver1)
    cloudsLoader.addImage("cloud3", cloudSize, R.drawable.cloud2ver2)
    cloudsLoader.addImage("cloud4", cloudSize, R.drawable.cloud2ver3)

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//////////    Manhole     ///////////////////////////////////////////////////////////////////////////////////////

    val manholeBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.manhole  )
    val manhole2Bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.manhole  )

    manhole =  Bitmap.createScaledBitmap( manholeBitmap, 480, 480, true ).asImageBitmap()
    manhole2 =  Bitmap.createScaledBitmap( manhole2Bitmap, 280, 280, true ).asImageBitmap()

    manholeItem.add( manhole )
    manholeItem.add( manhole2)

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    var obstaclePositionScale: Double = if ( deviceWidthInPixels > 1080 ) 0.44 else 0.60
    val obst1: Int = (obstaclePositionScale * deviceWidthInPixels - ( deviceWidthInPixels * 0.19 + deviceWidthInPixels * 0.01)).toInt()
    val obst2: Int = obst1 - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.02 ).toInt()
    val obst3: Int = obst2 - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.02 ).toInt()
    val obstXposs: List<Int> = listOf(obst1,  obst2, obst3);

    val scState = rememberScaffoldState()
    //Start screen based on authentication
    if( userVM.user.value == null ){
        LoginRegister( userVM, auth )
    } else {
        val ownGames = gameVM.ownGames.value
        val navControl = rememberNavController()
        NavHost(navController = navControl, startDestination = "MainScreen"){
            composable( route = "MainScreen"){
                MainScreen( navControl, userVM, auth, scState )
            }
            composable( route = "GameScreen"){
                GameScreen( userVM,
                    gameVM,
                    GameState(gameVM = gameVM),
                    catWalkLoader.imagesWithName,
                    catJumpLoader.imagesWithName,
                    catCrawlLoader.imagesWithName,
                    obstacleLoader.imagesWithName,
                    backgroundObjectsLoader.imagesWithName,
                    cloudsLoader.imagesWithName,
                    greeneryLoader.imagesWithName,
                    manholeItem,
                    obstXposs,
                    controller
                )
            }
            composable( route = "UserInfo"){
                UserInfo( navControl, userVM, scState )
            }
            composable( route = "HighScores"){
                HighScores( navControl, scState, gameVM, userVM )
            }
            composable( route = "GameHistory"){
                GameHistory( navControl, userVM, gameVM, scState )
            }
            ownGames.forEach { game ->
                composable( route = game.timestamp.toString()){
                    SavedGameScreen( navControl, scState, game )
                }
            }
            composable( route = "AboutUs"){
                AboutUs( navControl, scState )
            }
        }
    }

}
}