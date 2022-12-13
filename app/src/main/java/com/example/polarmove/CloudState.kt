package com.example.polarmove

import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap

data class CloudState(
    val cloudList: ArrayList<CloudModel> = ArrayList(),
    var cloudItems: ArrayList<ImageBitmap>,
    val obst1: Int = deviceWidthInPixels - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.18).toInt(),
    val obst2: Int = obst1 - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.15 ).toInt(),
    val obst3: Int = obst2 - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.12 ).toInt(),
    val obstXposs: List<Int> = listOf(obst1,  obst2, obst3)
) {

    init {
        initClouds()
        Log.d("MESSAGE", "SSSSSSSS")
    }

    fun initClouds() {
        cloudList.clear()
        var startX = -1500
        var obstacleCount = 9
        val imageCount = (0..3)

        for ( i in 0 until obstacleCount ) {
            val count = (0..2)
            val clouds = CloudModel(

//                xPos = xPossibilities[(0..2).random()],
                xPos = startX,
                yPos = obstXposs[count.random()],
                image = cloudItems[imageCount.random()]
            )
            cloudList.add(clouds)

            startX += 500
        }
    }

    fun moveDown() {
        cloudList.forEach { clouds ->
            clouds.xPos += obstacleSpeed * 3
            Log.d("Cloud X", clouds.xPos.toString())
        }
        val imageCount = (0..3)
        val count = (0..2)

        if ( cloudList.first().xPos >= deviceWidthInPixels + 60 ) {
            cloudList.removeAt(0)
            val clouds = CloudModel(
//                xPos = xPossibilities[(0..2).random()],
                xPos = nextObstacleY(cloudList.last().xPos),
                yPos = obstXposs[count.random()],
                image = cloudItems[imageCount.random()]
            )
            cloudList.add(clouds)
        }
    }


    fun nextObstacleY(lastY: Int): Int
    {
        var nextY = lastY - 1500
        return nextY
    }

}

data class CloudModel(
    var xPos: Int,
    var yPos: Int,
    var zLevel: Int = 1,
    var image: ImageBitmap
//    var size: Int = (deviceWidthInPixels * 0.16).toInt()
) {

}