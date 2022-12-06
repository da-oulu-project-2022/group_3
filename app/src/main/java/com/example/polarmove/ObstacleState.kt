package com.example.polarmove

import androidx.compose.ui.geometry.Rect

data class ObstacleState(
    val obstacleList: ArrayList<ObstacleModel> = ArrayList(),
    val obst1: Int = deviceWidthInPixels - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.01).toInt(),
    val obst2: Int = obst1 - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.02 ).toInt(),
    val obst3: Int = obst2 - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.02 ).toInt(),
    val obstXposs: List<Int> = listOf(obst1,  obst2, obst3)
) {

    init {
        initObstacle()
    }

    fun initObstacle() {
        obstacleList.clear()
        var startY = -200
        var obstacleCount = 4

        for ( i in 0 until obstacleCount ) {
            var obstacle = ObstacleModel(
//                xPos = xPossibilities[(0..2).random()],
                xPos = obstXposs[(0..2).random()],
                yPos = startY

            )
            obstacleList.add(obstacle)

            startY -= distanceBetweenObstacles
        }
    }

    fun moveDown() {
        obstacleList.forEach { obstacle ->
            obstacle.yPos += obstacleSpeed
        }

        if ( obstacleList.first().yPos >= deviceHeightInPixels + 100 ) {
            obstacleList.removeAt(0)
            var obstacle = ObstacleModel(
//                xPos = xPossibilities[(0..2).random()],
                xPos = obstXposs[( 0..2).random()],
                yPos = nextObstacleY(obstacleList.last().yPos)
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
    var xPos: Int = 0,
    var yPos: Int = 0,
    var zLevel: Int = 2,
    var size: Int = (deviceWidthInPixels * 0.16).toInt()
) {
    fun getBounds(): Rect {
        return Rect(
            left = xPos.toFloat(),
            top = yPos.toFloat(),
            right = xPos.toFloat() + size,
            bottom = yPos.toFloat() + size
        )
    }
}