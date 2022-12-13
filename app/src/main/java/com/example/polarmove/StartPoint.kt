package com.example.polarmove

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.polar.sdk.api.PolarBleApi

@Composable
fun StartPoint( api: PolarBleApi, height: Int, width: Int, gameVM: GameVM ){

    val userVM: UserVM = viewModel()
//    val gameVM: GameVM = viewModel()

    val auth = FirebaseAuth.getInstance()
    val user: FirebaseUser? = auth.currentUser

    //Fetching user data and set user if Firebase user is logged in
    if( user != null ){
        userVM.setEmail( user.email.toString() )
        userVM.fetchUserData()
        gameVM.getOwnGames( user.email.toString() )
        gameVM.getHighScores()
        userVM.setUser( user )
    }

    val walk1: ImageBitmap
    val walk2: ImageBitmap
    val walkCycle: ArrayList<ImageBitmap> = ArrayList()

    val jump1: ImageBitmap
    val jump2: ImageBitmap
    val jumpCycle: ArrayList<ImageBitmap> = ArrayList()

    val crawl1: ImageBitmap
    val crawl2: ImageBitmap
    val crawlCycle: ArrayList<ImageBitmap> = ArrayList()

    val grandma: ImageBitmap
    val trashcan: ImageBitmap
    val trashcan2: ImageBitmap
    val roadblock: ImageBitmap
    val bench: ImageBitmap
    val benchCat: ImageBitmap
//    val streetlight: ImageBitmap
    val roadObjects: ArrayList<ImageBitmap> = ArrayList()

    var obstacleScale = 200
    var playerScaale = 250

    if ( deviceWidthInPixels > 1080 ) {
        obstacleScale = 250
        playerScaale = 300
        Log.d("AGAIN", "AGAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAIN")
    }

//        walk1 = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catwalkcycle1 ).asImageBitmap()
//        walk2 = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catwalkcycle2 ).asImageBitmap()

        val walk1bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catwalkcycle1 )
        val walk2bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catwalkcycle2 )
        walk1 = Bitmap.createScaledBitmap( walk1bitmap, playerScaale, playerScaale, true ).asImageBitmap()
        walk2 = Bitmap.createScaledBitmap( walk2bitmap, playerScaale, playerScaale, true ).asImageBitmap()

        walkCycle.add(walk1)
        walkCycle.add(walk2)

        val jump1bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catjumpcycle1)
        val jump2bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catjumpcycle2)
        jump1 = Bitmap.createScaledBitmap( jump1bitmap, playerScaale, playerScaale, true ).asImageBitmap()
        jump2 = Bitmap.createScaledBitmap( jump2bitmap, playerScaale, playerScaale, true ).asImageBitmap()

        jumpCycle.add(jump1)
        jumpCycle.add(jump2)

        val crawl1bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catcrouchcycle1)
        val crawl2bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catcrouchcycle2)
        crawl1 = Bitmap.createScaledBitmap( crawl1bitmap, playerScaale, playerScaale, true ).asImageBitmap()
        crawl2 = Bitmap.createScaledBitmap( crawl2bitmap, playerScaale, playerScaale, true ).asImageBitmap()

        crawlCycle.add( crawl1 )
        crawlCycle.add( crawl2 )

//        grandma = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.grandma ).asImageBitmap()
//        trashcan = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.trashcan ).asImageBitmap()
//        trashcan2 = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.trashcan2 ).asImageBitmap()
//        roadblock = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.roadblock ).asImageBitmap()
//        bench = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.bench ).asImageBitmap()
//        benchCat = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.bench_cat ).asImageBitmap()
//        streetlight = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.streetlight ).asImageBitmap()

        val grandmaBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.grandma )
        val trashcanBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.trashcan )
        val trascan2Bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.trashcan2 )
        val roadblockBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.roadblock )
        val benchBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.bench )
        val benchCatBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.bench_cat )
//        val streetlightBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.streetlight )

        grandma = Bitmap.createScaledBitmap( grandmaBitmap, obstacleScale, obstacleScale, true ).asImageBitmap()
        trashcan = Bitmap.createScaledBitmap( trashcanBitmap, obstacleScale, obstacleScale, true ).asImageBitmap()
        trashcan2 = Bitmap.createScaledBitmap( trascan2Bitmap, obstacleScale, obstacleScale, true ).asImageBitmap()
        roadblock = Bitmap.createScaledBitmap( roadblockBitmap, obstacleScale, obstacleScale, true ).asImageBitmap()
        bench = Bitmap.createScaledBitmap( benchBitmap, obstacleScale, obstacleScale, true ).asImageBitmap()
        benchCat = Bitmap.createScaledBitmap( benchCatBitmap, obstacleScale, obstacleScale, true ).asImageBitmap()
//        streetlight = Bitmap.createScaledBitmap( streetlightBitmap, 280, 280, true ).asImageBitmap()

        roadObjects.add( grandma )
        roadObjects.add( trashcan )
        roadObjects.add( trashcan2 )
        roadObjects.add( roadblock )
        roadObjects.add( bench )
        roadObjects.add( benchCat )
//        roadObjects.add( streetlight )

//        Log.d("COUNTER", "COUUUUUUUUNT")
//
//        val walk1bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catwalkcycle1 )
//        val walk2bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catwalkcycle2 )
//        walk1 = Bitmap.createScaledBitmap( walk1bitmap, 380, 380, true ).asImageBitmap()
//        walk2 = Bitmap.createScaledBitmap( walk2bitmap, 380, 380, true ).asImageBitmap()
//
//        walkCycle.add(walk1)
//        walkCycle.add(walk2)
//
//        val grandmaBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.grandma )
//        val trashcanBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.trashcan )
//        val trascan2Bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.trashcan2 )
//        val roadblockBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.roadblock )
//        val benchBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.bench )
//        val benchCatBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.bench_cat )
////        val streetlightBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.streetlight )
//
//        grandma = Bitmap.createScaledBitmap( grandmaBitmap, 200, 200, true ).asImageBitmap()
//        trashcan = Bitmap.createScaledBitmap( trashcanBitmap, 200, 200, true ).asImageBitmap()
//        trashcan2 = Bitmap.createScaledBitmap( trascan2Bitmap, 200, 200, true ).asImageBitmap()
//        roadblock = Bitmap.createScaledBitmap( roadblockBitmap, 200, 200, true ).asImageBitmap()
//        bench = Bitmap.createScaledBitmap( benchBitmap, 200, 200, true ).asImageBitmap()
//        benchCat = Bitmap.createScaledBitmap( benchCatBitmap, 200, 200, true ).asImageBitmap()
////        streetlight = Bitmap.createScaledBitmap( streetlightBitmap, 320, 320, true ).asImageBitmap()
//
//        roadObjects.add( grandma )
//        roadObjects.add( trashcan )
//        roadObjects.add( trashcan2 )
//        roadObjects.add( roadblock )
//        roadObjects.add( bench )
//        roadObjects.add( benchCat )
//        roadObjects.add( streetlight )



//    val obst1: Int = deviceWidthInPixels - (distanceBetweenLines + deviceWidthInPixels * 0.18 + deviceWidthInPixels * 0.01).toInt()
//    val obst2: Int = obst1 - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.02 ).toInt()
//    val obst3: Int = obst2 - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.02 ).toInt()
    var obstaclePositionScale: Double = if ( deviceWidthInPixels > 1080 ) 0.44 else 0.60
    val obst1: Int = (obstaclePositionScale * deviceWidthInPixels - ( deviceWidthInPixels * 0.19 + deviceWidthInPixels * 0.01)).toInt()
    val obst2: Int = obst1 - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.02 ).toInt()
    val obst3: Int = obst2 - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.02 ).toInt()
    val obstXposs: List<Int> = listOf(obst1,  obst2, obst3)

    Log.d( "HEIGHT", deviceHeightInPixels.toString())
    Log.d("Width", deviceWidthInPixels.toString())
    Log.d( "Possisions", obstXposs.toString())

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
                GameScreen(navControl, userVM, gameVM, api, height, width, GameState(), walkCycle, jumpCycle, crawlCycle, roadObjects, obstXposs )
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