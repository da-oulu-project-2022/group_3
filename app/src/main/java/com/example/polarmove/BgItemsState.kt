package com.example.polarmove

import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap

data class BgItemsState(
    val bgItemList: ArrayList<BgItemsModel> = ArrayList(),
    var backgroundObjects: ArrayList<ImageBitmap>,
    val obstXposs: List<Int> = listOf(-185,-195,-190)
) {

    init {
        initBgItems()
//        Log.d("MESSAGE", "SSSSSSSS")
    }

    fun initBgItems() {
        bgItemList.clear()
        var startY = 1000
        var obstacleCount = 8
        val imageCount = (0..14)

        for ( i in 0 until obstacleCount ) {
            val count = (0..2)
            val background = BgItemsModel(
                xPos = obstXposs[count.random()],
                yPos = startY,
                image = backgroundObjects[imageCount.random()]
            )
            bgItemList.add(background)

            startY -= 1000
        }
    }

    fun moveDown() {
        bgItemList.forEach { background ->
            background.yPos += obstacleSpeed * 5
//            Log.d("BG Y", background.yPos.toString())
        }
        val imageCount = (0..14)
        val count = (0..2)

        if ( bgItemList.first().yPos >= deviceHeightInPixels + 100 ) {
            bgItemList.removeAt(0)
            val backgroundItem = BgItemsModel(
                xPos = obstXposs[count.random()],
                yPos = nextItemY(bgItemList.last().yPos),
                image = backgroundObjects[imageCount.random()]
            )
            bgItemList.add(backgroundItem)
        }
    }

    private fun nextItemY(lastY: Int): Int
    {
        var nextY = lastY - 1000
        return nextY
    }

}

data class BgItemsModel(
    var xPos: Int,
    var yPos: Int,
    var zLevel: Int = 1,
    var image: ImageBitmap
//    var size: Int = (deviceWidthInPixels * 0.16).toInt()
) {

}

data class BgGreeneryItemsState(
    val bgGreeneryItemList: ArrayList<BgGreeneryModel> = ArrayList(),
    var greeneryObjects: ArrayList<ImageBitmap>,
    val obstXposs: List<Int> = listOf(50,50,50)
) {

    init {
        initBgGreeneryItems()
//        Log.d("MESSAGE", "SSSSSSSS")
    }

    fun initBgGreeneryItems() {
        bgGreeneryItemList.clear()
        var startY = -5000
        var obstacleCount = 4
        val imageCount = (0..6)

        for ( i in 0 until obstacleCount ) {
            val count = (0..2)
            val greenery = BgGreeneryModel(
                xPos = obstXposs[count.random()],
                yPos = startY,
                image = greeneryObjects[imageCount.random()]
            )
            bgGreeneryItemList.add(greenery)

            startY -= 6000
        }
    }

    fun moveDown() {
        bgGreeneryItemList.forEach { greenery ->
            greenery.yPos += obstacleSpeed * 5
//            Log.d("BG Y", greenery.yPos.toString())
        }
        val imageCount = (0..6)
        val count = (0..2)

        if ( bgGreeneryItemList.first().yPos >= deviceHeightInPixels + 100 ) {
            bgGreeneryItemList.removeAt(0)
            val greenery = BgGreeneryModel(
                xPos = obstXposs[count.random()],
                yPos = nextItemY(bgGreeneryItemList.last().yPos),
                image = greeneryObjects[imageCount.random()]
            )
            bgGreeneryItemList.add(greenery)
        }
    }

    private fun nextItemY(lastY: Int): Int
    {
        var nextY = lastY - 5000
        return nextY
    }

}

data class BgGreeneryModel(
    var xPos: Int,
    var yPos: Int,
    var zLevel: Int = 1,
    var image: ImageBitmap
//    var size: Int = (deviceWidthInPixels * 0.16).toInt()
) {}
