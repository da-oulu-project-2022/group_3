package com.example.polarmove

import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap

data class CloudState(
    val cloudList: ArrayList<CloudModel> = ArrayList(),
    var cloudItems: ArrayList<ImageBitmap>,
//    val obst1: Int = deviceHeightInPixels - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.18).toInt(),
//    val obst2: Int = obst1 - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.15 ).toInt(),
//    val obst3: Int = obst2 - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.12 ).toInt(),
    val cloudPosition: List<Int> = listOf( 200, 600, 1000, 1400 )
) {

    init {
        initClouds()
    }

    fun initClouds() {
        cloudList.clear()
        var startX = -200
        var obstacleCount = 5
        val imageCount = (0..3)

        for ( i in 0 until obstacleCount ) {
            val positionOptions = (0..3)
            val clouds = CloudModel(

//                xPos = xPossibilities[(0..2).random()],
                xPos = startX,
                yPos = cloudPosition[positionOptions.random()],
                image = cloudItems[imageCount.random()]
            )
            cloudList.add(clouds)

            startX -= 500
        }
    }

    fun moveRight() {
        cloudList.forEach { clouds ->
            clouds.xPos += obstacleSpeed * 3
        }
        val imageCount = (0..3)
        val positionOptions = (0..3)

        if ( cloudList.first().xPos >= deviceWidthInPixels + 60 ) {
            cloudList.removeAt(0)
            val cloud = CloudModel(
//                xPos = xPossibilities[(0..2).random()],
                xPos = nextCloudX(cloudList.last().xPos),
                yPos = cloudPosition[positionOptions.random()],
                image = cloudItems[imageCount.random()]
            )
            cloudList.add(cloud)
        }
    }


    fun nextCloudX(lastX: Int): Int
    {
        var nextY = lastX - 1500
        return nextY
    }

}

data class CloudModel(
    var xPos: Int,
    var yPos: Int,
    var image: ImageBitmap
) {

}