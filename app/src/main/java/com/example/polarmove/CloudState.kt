package com.example.polarmove

import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap

data class cloudState(
    val cloudList: ArrayList<CloudModel> = ArrayList(),
    var cloudItems: ArrayList<ImageBitmap>,
    val obst1: Int = deviceWidthInPixels - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.01).toInt(),
    val obst2: Int = obst1 - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.02 ).toInt(),
    val obst3: Int = obst2 - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.02 ).toInt(),
    val obstXposs: List<Int> = listOf(obst1,  obst2, obst3)
) {

    init {
        initClouds()
        Log.d("MESSAGE", "SSSSSSSS")
    }

    fun initClouds() {
        cloudList.clear()
        var startY = -200
        var obstacleCount = 4
        val imageCount = (0..5)

        for ( i in 0 until obstacleCount ) {
            val count = (0..2)
            val clouds = CloudModel(
//                xPos = xPossibilities[(0..2).random()],
                xPos = obstXposs[count.random()],
                yPos = startY,
                image = cloudItems[imageCount.random()]
            )
            cloudList.add(clouds)

            startY += distanceBetweenObstacles
        }
    }

    fun moveDown() {
        cloudList.forEach { clouds ->
            clouds.yPos += obstacleSpeed * 3
            Log.d("Cloud Y", clouds.yPos.toString())
        }
        val imageCount = (0..5)
        val count = (0..2)

        if ( cloudList.first().yPos >= deviceWidthInPixels - 100 ) {
            cloudList.removeAt(0)
            val clouds = CloudModel(
//                xPos = xPossibilities[(0..2).random()],
                xPos = obstXposs[count.random()],
                yPos = nextObstacleY(cloudList.last().yPos),
                image = cloudItems[imageCount.random()]
            )
            cloudList.add(clouds)
        }
    }


    fun nextObstacleY(lastY: Int): Int
    {
        var nextY = lastY - distanceBetweenObstacles
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