package com.example.polarmove

import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap

data class CloudState(
    val cloudList: ArrayList<CloudModel> = ArrayList(),
    var cloudItems: ArrayList<ImageLoader.ImageWithName>,
    val cloudPosition: List<Int> = listOf( 200, 600, 1000, 1400 )
) {

    init {
        initClouds()
    }

    fun initClouds() {
        cloudList.clear()
        var startX = -200
        var obstacleCount = 3
        val imageCount = (0..2)

        for ( i in 0 until obstacleCount ) {
            val positionOptions = (0..3)
            val random = imageCount.random()
            val clouds = CloudModel(
                xPos = startX,
                yPos = cloudPosition[positionOptions.random()],
                image = cloudItems[random].image
            )
            cloudList.add(clouds)

            startX -= 500
        }
    }

    fun moveRight() {
        cloudList.forEach { clouds ->
            clouds.xPos += obstacleSpeed * 3
        }
        val imageCount = (0..2)
        val positionOptions = (0..3)

        if ( cloudList.first().xPos >= deviceWidthInPixels + 60 ) {
            val random = imageCount.random()
            cloudList.removeAt(0)
            val cloud = CloudModel(
                xPos = nextCloudX(cloudList.last().xPos),
                yPos = cloudPosition[positionOptions.random()],
                image = cloudItems[random].image
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