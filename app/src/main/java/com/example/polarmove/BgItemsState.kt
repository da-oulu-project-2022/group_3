package com.example.polarmove

import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap

data class bgItemsState(
    val bgItemList: ArrayList<bgItemsModel> = ArrayList(),
    var backgroundObjects: ArrayList<ImageBitmap>,
    val obstXposs: List<Int> = listOf(-200,-250,-300)
) {

    init {
        initBgItems()
        Log.d("MESSAGE", "SSSSSSSS")
    }

    fun initBgItems() {
        bgItemList.clear()
        var startY = 2000
        var obstacleCount = 4
        val imageCount = (0..14)

        for ( i in 0 until obstacleCount ) {
            val count = (0..2)
            val background = bgItemsModel(
                xPos = obstXposs[count.random()],
                yPos = startY,
                image = backgroundObjects[imageCount.random()]
            )
            bgItemList.add(background)

            startY -= 2000
        }
    }

    fun moveDown() {
        bgItemList.forEach { background ->
            background.yPos += obstacleSpeed * 3
            Log.d("BG Y", background.yPos.toString())
        }
        val imageCount = (0..5)
        val count = (0..2)

        if ( bgItemList.first().yPos >= deviceHeightInPixels + 100 ) {
            bgItemList.removeAt(0)
            val backgroundItem = bgItemsModel(
                xPos = obstXposs[count.random()],
                yPos = nextItemY(bgItemList.last().yPos),
                image = backgroundObjects[imageCount.random()]
            )
            bgItemList.add(backgroundItem)
        }
    }

    fun nextItemY(lastY: Int): Int
    {
        var nextY = lastY - 2000
        return nextY
    }

}

data class bgItemsModel(
    var xPos: Int,
    var yPos: Int,
    var zLevel: Int = 1,
    var image: ImageBitmap
//    var size: Int = (deviceWidthInPixels * 0.16).toInt()
) {

}