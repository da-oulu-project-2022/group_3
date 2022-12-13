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

    var obstacleSize = 200
    var playerSize = 250
    var bgItemSize = 700
    var cloudSize = 700
    var plantsSize = 300
    
    if( deviceWidthInPixels > 1080){
        obstacleSize = 250
        playerSize = 300
        bgItemSize = 800
        cloudSize = 800
        plantsSize = 400
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

    //   Manhole
    val manhole: ImageBitmap
    val manhole2: ImageBitmap
    val manholeItem: ArrayList<ImageBitmap> = ArrayList()

//    Background objects
    val house: ImageBitmap
    val house2: ImageBitmap
    val house3: ImageBitmap
    val house4: ImageBitmap
    val house5: ImageBitmap
    val house6: ImageBitmap
    val bush: ImageBitmap
    val bush2: ImageBitmap
    val bush3: ImageBitmap
    val tree: ImageBitmap
    val tree2: ImageBitmap
    val flower: ImageBitmap
    val greenery: ImageBitmap
    val streetlight: ImageBitmap
    val trashcanbg: ImageBitmap
    val backgroundObjects: ArrayList<ImageBitmap> = ArrayList()

    val bushVer2: ImageBitmap
    val bush2Ver2: ImageBitmap
    val bush3Ver2: ImageBitmap
    val treeVer2: ImageBitmap
    val tree2Ver2: ImageBitmap
    val flowerVer2: ImageBitmap
    val greeneryVer2: ImageBitmap
    val greeneryObjects: ArrayList<ImageBitmap> = ArrayList()

//    Clouds
    val cloud: ImageBitmap
    val cloud2: ImageBitmap
    val cloud3: ImageBitmap
    val cloud4: ImageBitmap
    val cloudItems: ArrayList<ImageBitmap> = ArrayList()



//        walk1 = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catwalkcycle1 ).asImageBitmap()
//        walk2 = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catwalkcycle2 ).asImageBitmap()

        val walk1bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catwalkcycle1 )
        val walk2bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.catwalkcycle2 )
        walk1 = Bitmap.createScaledBitmap( walk1bitmap, playerSize, playerSize, true ).asImageBitmap()
        walk2 = Bitmap.createScaledBitmap( walk2bitmap, playerSize, playerSize, true ).asImageBitmap()

        walkCycle.add(walk1)
        walkCycle.add(walk2)

        val grandmaBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.grandma )
        val trashcanBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.trashcan )
        val trascan2Bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.trashcan2 )
        val roadblockBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.roadblock )
        val benchBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.bench )
        val benchCatBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.bench_cat )
//        val streetlightBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.streetlight )

//          Background objects
        val houseBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.house1version1  )
        val house2Bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.house1version2 )
        val house3Bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.house1version3 )
        val house4Bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.house2version1 )
        val house5Bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.house2version2 )
        val house6Bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.house2version3 )
        val bushBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.bush )
        val bush2Bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.bush2  )
        val bush3Bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.bush3  )
        val treeBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.tree1 )
        val tree2Bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.tree2  )
        val flowerBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.flower  )
        val greeneryBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.greenery )
        val streetlightBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.streetlight )
        val trashcanbgBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.trashcan  )

//           Clouds
        val cloudBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.cloud  )
        val cloud2Bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.cloud2ver1  )
        val cloud3Bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.cloud2ver2)
        val cloud4Bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.cloud2ver3  )

//          Manhole
        val manholeBitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.manhole  )
        val manhole2Bitmap = BitmapFactory.decodeResource( LocalContext.current.resources, R.drawable.manhole  )

//           Obstacles
        grandma = Bitmap.createScaledBitmap( grandmaBitmap, obstacleSize, obstacleSize, true ).asImageBitmap()
        trashcan = Bitmap.createScaledBitmap( trashcanBitmap, obstacleSize, obstacleSize, true ).asImageBitmap()
        trashcan2 = Bitmap.createScaledBitmap( trascan2Bitmap, obstacleSize, obstacleSize, true ).asImageBitmap()
        roadblock = Bitmap.createScaledBitmap( roadblockBitmap, obstacleSize, obstacleSize, true ).asImageBitmap()
        bench = Bitmap.createScaledBitmap( benchBitmap, obstacleSize, obstacleSize, true ).asImageBitmap()
        benchCat = Bitmap.createScaledBitmap( benchCatBitmap, obstacleSize, obstacleSize, true ).asImageBitmap()
//        streetlight = Bitmap.createScaledBitmap( streetlightBitmap, 270, 270, true ).asImageBitmap()

//        Background objects
        house = Bitmap.createScaledBitmap( houseBitmap, bgItemSize, bgItemSize, true ).asImageBitmap()
        house2 = Bitmap.createScaledBitmap( house2Bitmap,bgItemSize+200, bgItemSize+200, true ).asImageBitmap()
        house3 = Bitmap.createScaledBitmap( house3Bitmap,bgItemSize+200, bgItemSize+200, true ).asImageBitmap()
        house4 = Bitmap.createScaledBitmap( house4Bitmap,bgItemSize, bgItemSize, true ).asImageBitmap()
        house5 = Bitmap.createScaledBitmap( house5Bitmap,bgItemSize, bgItemSize, true ).asImageBitmap()
        house6 = Bitmap.createScaledBitmap( house6Bitmap,bgItemSize, bgItemSize, true ).asImageBitmap()
        bush = Bitmap.createScaledBitmap( bushBitmap, plantsSize, plantsSize, true ).asImageBitmap()
        bush2 = Bitmap.createScaledBitmap( bush2Bitmap, plantsSize+200, plantsSize+200, true ).asImageBitmap()
        bush3 = Bitmap.createScaledBitmap( bush3Bitmap, plantsSize, plantsSize, true ).asImageBitmap()
        tree = Bitmap.createScaledBitmap( treeBitmap, bgItemSize-300, bgItemSize-300, true ).asImageBitmap()
        tree2 = Bitmap.createScaledBitmap( tree2Bitmap, plantsSize+200, plantsSize+200, true ).asImageBitmap()
        flower = Bitmap.createScaledBitmap( flowerBitmap, plantsSize-100, plantsSize-100, true ).asImageBitmap()
        greenery = Bitmap.createScaledBitmap( greeneryBitmap, plantsSize-100, plantsSize-100, true ).asImageBitmap()
        streetlight = Bitmap.createScaledBitmap( streetlightBitmap, bgItemSize, bgItemSize, true ).asImageBitmap()
        trashcanbg = Bitmap.createScaledBitmap( trashcanbgBitmap, bgItemSize-400, bgItemSize-450, true ).asImageBitmap()
//      ------------------------------------------------------------------------------------------------------------------------------------------------
        bushVer2 = Bitmap.createScaledBitmap( bushBitmap, plantsSize, plantsSize, true ).asImageBitmap()
        bush2Ver2 = Bitmap.createScaledBitmap( bush2Bitmap, plantsSize, plantsSize, true ).asImageBitmap()
        bush3Ver2 = Bitmap.createScaledBitmap( bush3Bitmap, plantsSize, plantsSize, true ).asImageBitmap()
        treeVer2 = Bitmap.createScaledBitmap( treeBitmap, bgItemSize-200, bgItemSize-200, true ).asImageBitmap()
        tree2Ver2 = Bitmap.createScaledBitmap( tree2Bitmap, plantsSize+200, plantsSize+200, true ).asImageBitmap()
        flowerVer2 = Bitmap.createScaledBitmap( flowerBitmap, plantsSize-100, plantsSize-100, true ).asImageBitmap()
        greeneryVer2 = Bitmap.createScaledBitmap( greeneryBitmap, plantsSize-200, plantsSize-200, true ).asImageBitmap()

//           Clouds
        cloud = Bitmap.createScaledBitmap( cloudBitmap, cloudSize, cloudSize-200, true ).asImageBitmap()
        cloud2 = Bitmap.createScaledBitmap(cloud2Bitmap, cloudSize, cloudSize, true ).asImageBitmap()
        cloud3 =  Bitmap.createScaledBitmap( cloud3Bitmap, cloudSize, cloudSize, true ).asImageBitmap()
        cloud4 =  Bitmap.createScaledBitmap( cloud4Bitmap, cloudSize, cloudSize, true ).asImageBitmap()

//          Manhole
        manhole =  Bitmap.createScaledBitmap( manholeBitmap, 480, 480, true ).asImageBitmap()
        manhole2 =  Bitmap.createScaledBitmap( manhole2Bitmap, 280, 280, true ).asImageBitmap()

//      Add obstacles to obstacle roadObjects list
        roadObjects.add( grandma )
        roadObjects.add( trashcan )
        roadObjects.add( trashcan2 )
        roadObjects.add( roadblock )
        roadObjects.add( bench )
        roadObjects.add( benchCat )
//        roadObjects.add( streetlight )


//      Add Background objects to backgroundObjects list
        backgroundObjects.add( house )
        backgroundObjects.add( house2 )
        backgroundObjects.add( house3 )
        backgroundObjects.add( house4 )
        backgroundObjects.add( house5 )
        backgroundObjects.add( house6 )
        backgroundObjects.add( bush )
        backgroundObjects.add( bush2 )
        backgroundObjects.add( bush3 )
        backgroundObjects.add( tree )
        backgroundObjects.add( tree2 )
        backgroundObjects.add( flower )
        backgroundObjects.add( greenery )
        backgroundObjects.add( streetlight )
        backgroundObjects.add( trashcanbg )

        greeneryObjects.add( treeVer2 )
        greeneryObjects.add( tree2Ver2 )
        greeneryObjects.add( flowerVer2 )
        greeneryObjects.add( greeneryVer2 )
        greeneryObjects.add( bushVer2 )
        greeneryObjects.add( bush2Ver2 )
        greeneryObjects.add( bush3Ver2 )


        manholeItem.add( manhole )
        manholeItem.add( manhole2)
//       Add Clouds objects to cloudItems list
        cloudItems.add( cloud )
        cloudItems.add( cloud2 )
        cloudItems.add( cloud3 )
        cloudItems.add( cloud4 )


        Log.d("COUNTER", "COUUUUUUUUNT")

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
                GameScreen(navControl, userVM, gameVM, api, hr, height, width, GameState(), walkCycle, roadObjects, backgroundObjects, cloudItems, greeneryObjects, manholeItem )
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