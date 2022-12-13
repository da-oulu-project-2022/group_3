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
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay

class PlayerState(
    var xPos: Int = 0,
    var yPos: Int = 0,
    var zLevel: Int = 0,
    var keyframe: Int = 0,
//    var walk1: ImageBitmap,
//    var walk2: ImageBitmap,
//    private var bitMapList: ArrayList<ImageBitmap> = arrayListOf(),
    var walkCycle: ArrayList<ImageBitmap>,
    var jumpCycle: ArrayList<ImageBitmap>,
    var crawlCycle: ArrayList<ImageBitmap>,
    var isJumping: Boolean = false,
    var isCrawling: Boolean = false,
//    val pos1: Int = deviceWidthInPixels - (distanceBetweenLines + deviceWidthInPixels * 0.2 + deviceWidthInPixels * 0.01).toInt(),
    var playerPositionScale: Double = if ( deviceWidthInPixels > 1080 ) 0.44 else 0.60,
    val pos1: Int = ( playerPositionScale * deviceWidthInPixels - (distanceBetweenLines + deviceWidthInPixels * 0.2 + deviceWidthInPixels * 0.01)).toInt(),
    val pos2: Int = pos1 - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.02 ).toInt(),
    val pos3: Int = pos2 - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.02 ).toInt()
) {

    val image: ImageBitmap
//        get() = if ( keyframe <= 10) bitMapList[0] else bitMapList[1]
//        get() = if ( keyframe <= 10) walkCycle[0] else walkCycle[1]
//        get() = if ( keyframe <= 25) jumpCycle[0] else jumpCycle[1]
        get() = if ( isJumping && keyframe <= 38 ) {
            jumpCycle[0]
        } else if ( isJumping && keyframe > 38 ){
            jumpCycle[1]
        } else if( isCrawling && keyframe <= 20) {
            crawlCycle[0]
        } else if ( isCrawling && keyframe > 20){
            crawlCycle[1]
        } else if ( keyframe <=10) {
            walkCycle[0]
        } else {
            walkCycle[1]
        }

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

    fun move() {
        changeKeyframe()
    }

    fun changeKeyframe() {
        keyframe ++
        if ( isJumping && keyframe == 76 ) {
            isJumping = false
            keyframe = 0
        } else if ( !isJumping && !isCrawling && keyframe == 20 ){
            keyframe = 0
        } else if ( isCrawling && keyframe == 40) {
            keyframe = 0
        }
    }

    fun moveLeft() {
        xPos = if ( xPos == pos1 ) pos2 else pos3
    }

    fun moveRight() {
        xPos = if ( xPos == pos3 ) pos2 else pos1
    }

    fun jump() {
        isCrawling = false
        isJumping = true
        keyframe = 0
    }

    fun crawl() {
        isCrawling = !isCrawling
        keyframe = 0
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