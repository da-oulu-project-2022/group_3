package com.example.polarmove

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toDrawable

class PlayerState(
    var xPos: Int = 0,
    var yPos: Int = 0,
    var zLevel: Int = 0,
    var keyframe: Int = 0,
//    var walk1: ImageBitmap,
//    var walk2: ImageBitmap,
//    private var bitMapList: ArrayList<ImageBitmap> = arrayListOf(),
    var walkCycle: ArrayList<ImageBitmap>,
    var isJumping: Boolean = false,
    var isCrawling: Boolean = false,
    val pos1: Int = deviceWidthInPixels - (distanceBetweenLines + deviceWidthInPixels * 0.18 + deviceWidthInPixels * 0.01).toInt(),
    val pos2: Int = pos1 - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.02 ).toInt(),
    val pos3: Int = pos2 - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.02 ).toInt()
) {

    val image: ImageBitmap
//        get() = if ( keyframe <= 10) bitMapList[0] else bitMapList[1]
        get() = if ( keyframe <= 10) walkCycle[0] else walkCycle[1]

    init {
//        bitMapList.add( walk1 )
//        bitMapList.add( walk2 )
        playerInit()
    }

    fun playerInit(){
//        xPos = 0
        xPos = pos2
        yPos = (deviceHeightInPixels - (deviceHeightInPixels * 0.15 + distanceBetweenLines * 3)).toInt()
        zLevel = 0
        isJumping = false
        isCrawling = false
    }

    fun run(){
        if ( !isJumping && !isCrawling ) {
            changeKeyframe()
        }
//        Log.d("sdsds", "RUNNNNNNNNn")
    }

    fun changeKeyframe() {
        keyframe ++
        if ( keyframe == 20 ) {
            keyframe = 0
        }
    }

    fun moveLeft() {
        xPos = if ( xPos == pos1 ) pos2 else pos3
    }

    fun moveRight() {
        xPos = if ( xPos == pos3 ) pos2 else pos1
    }

    fun getBounds(): Rect {
        return Rect(
            left = xPos.toFloat(),
            top = yPos.toFloat(),
            right = (xPos + image.width).toFloat(),
            bottom = (yPos + image.height).toFloat()
        )
    }

}