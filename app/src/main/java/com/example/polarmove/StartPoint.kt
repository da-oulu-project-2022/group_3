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
fun StartPoint( api: PolarBleApi, hr: Int, height: Int, width: Int){

    val userVM: UserVM = viewModel()
    val gameVM: GameVM = viewModel()

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

    val grandma: ImageBitmap
    val trashcan: ImageBitmap
    val trashcan2: ImageBitmap
    val roadblock: ImageBitmap
    val bench: ImageBitmap
    val benchCat: ImageBitmap
//    val streetlight: ImageBitmap
    val roadObjects: ArrayList<ImageBitmap> = ArrayList()

    if ( deviceWidthInPixels > 1080 ) {
//        walk1 = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catwalkcycle1 ).asImageBitmap()
//        walk2 = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catwalkcycle2 ).asImageBitmap()

        val walk1bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catwalkcycle1 )
        val walk2bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catwalkcycle2 )
        walk1 = Bitmap.createScaledBitmap( walk1bitmap, 280, 280, true ).asImageBitmap()
        walk2 = Bitmap.createScaledBitmap( walk2bitmap, 280, 280, true ).asImageBitmap()

        walkCycle.add(walk1)
        walkCycle.add(walk2)

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

        grandma = Bitmap.createScaledBitmap( grandmaBitmap, 280, 280, true ).asImageBitmap()
        trashcan = Bitmap.createScaledBitmap( trashcanBitmap, 280, 280, true ).asImageBitmap()
        trashcan2 = Bitmap.createScaledBitmap( trascan2Bitmap, 280, 280, true ).asImageBitmap()
        roadblock = Bitmap.createScaledBitmap( roadblockBitmap, 280, 280, true ).asImageBitmap()
        bench = Bitmap.createScaledBitmap( benchBitmap, 280, 280, true ).asImageBitmap()
        benchCat = Bitmap.createScaledBitmap( benchCatBitmap, 280, 280, true ).asImageBitmap()
//        streetlight = Bitmap.createScaledBitmap( streetlightBitmap, 280, 280, true ).asImageBitmap()

        roadObjects.add( grandma )
        roadObjects.add( trashcan )
        roadObjects.add( trashcan2 )
        roadObjects.add( roadblock )
        roadObjects.add( bench )
        roadObjects.add( benchCat )
//        roadObjects.add( streetlight )

        Log.d("COUNTER", "COUUUUUUUUNT")
    } else {
        val walk1bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catwalkcycle1 )
        val walk2bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catwalkcycle2 )
        walk1 = Bitmap.createScaledBitmap( walk1bitmap, 380, 380, true ).asImageBitmap()
        walk2 = Bitmap.createScaledBitmap( walk2bitmap, 380, 380, true ).asImageBitmap()

        walkCycle.add(walk1)
        walkCycle.add(walk2)

        val grandmaBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.grandma )
        val trashcanBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.trashcan )
        val trascan2Bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.trashcan2 )
        val roadblockBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.roadblock )
        val benchBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.bench )
        val benchCatBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.bench_cat )
//        val streetlightBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.streetlight )

        grandma = Bitmap.createScaledBitmap( grandmaBitmap, 320, 320, true ).asImageBitmap()
        trashcan = Bitmap.createScaledBitmap( trashcanBitmap, 320, 320, true ).asImageBitmap()
        trashcan2 = Bitmap.createScaledBitmap( trascan2Bitmap, 320, 320, true ).asImageBitmap()
        roadblock = Bitmap.createScaledBitmap( roadblockBitmap, 320, 320, true ).asImageBitmap()
        bench = Bitmap.createScaledBitmap( benchBitmap, 320, 320, true ).asImageBitmap()
        benchCat = Bitmap.createScaledBitmap( benchCatBitmap, 320, 320, true ).asImageBitmap()
//        streetlight = Bitmap.createScaledBitmap( streetlightBitmap, 320, 320, true ).asImageBitmap()

        roadObjects.add( grandma )
        roadObjects.add( trashcan )
        roadObjects.add( trashcan2 )
        roadObjects.add( roadblock )
        roadObjects.add( bench )
        roadObjects.add( benchCat )
//        roadObjects.add( streetlight )

    }

    Log.d( "HEIGHT", deviceHeightInPixels.toString())
    Log.d("Width", deviceWidthInPixels.toString())

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
                GameScreen(navControl, userVM, gameVM, api, hr, height, width, GameState(), walkCycle, roadObjects )
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