package com.example.polarmove

import android.util.Log
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ImageBitmap

data class ObstacleState(
    val obstacleList: ArrayList<ObstacleModel> = ArrayList(),
    var roadObjects: ArrayList<ImageBitmap>,
    val obst1: Int = deviceWidthInPixels - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.01).toInt(),
    val obst2: Int = obst1 - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.02 ).toInt(),
    val obst3: Int = obst2 - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.02 ).toInt(),
    val obstXposs: List<Int> = listOf(obst1,  obst2, obst3)
) {

    init {
        initObstacle()
        Log.d("MESSAGE", "SSSSSSSS")
    }

    fun initObstacle() {
        obstacleList.clear()
        var startY = -200
        var obstacleCount = 4
        val imageCount = (0..5)

        for ( i in 0 until obstacleCount ) {
            val count = (0..2)
            val obstacle = ObstacleModel(
//                xPos = xPossibilities[(0..2).random()],
                xPos = obstXposs[count.random()],
                yPos = startY,
                image = roadObjects[imageCount.random()]
            )
            obstacleList.add(obstacle)

            startY -= distanceBetweenObstacles
        }
    }

    fun moveDown() {
        obstacleList.forEach { obstacle ->
            obstacle.yPos += obstacleSpeed * 3
            Log.d("OBSTACLE Y", obstacle.yPos.toString())
        }
        val imageCount = (0..5)
        val count = (0..2)

        if ( obstacleList.first().yPos >= deviceHeightInPixels + 100 ) {
            obstacleList.removeAt(0)
            val obstacle = ObstacleModel(
//                xPos = xPossibilities[(0..2).random()],
                xPos = obstXposs[count.random()],
                yPos = nextObstacleY(obstacleList.last().yPos),
                image = roadObjects[imageCount.random()]
            )
            obstacleList.add(obstacle)
        }

    }

    fun nextObstacleY(lastY: Int): Int
    {
        var nextY = lastY - distanceBetweenObstacles
        return nextY
    }

}

data class ObstacleModel(
    var xPos: Int,
    var yPos: Int,
    var zLevel: Int = 1,
    var image: ImageBitmap
//    var size: Int = (deviceWidthInPixels * 0.16).toInt()
) {
    fun getBounds(): Rect {
        Log.d("RECTANGLE", yPos.toString())
        return Rect(
            left = xPos.toFloat(),
            top = yPos.toFloat(),
            right = (xPos + image.width).toFloat(),
            bottom = (yPos + image.height).toFloat()
        )
    }
}