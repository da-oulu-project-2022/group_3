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
import com.example.polarmove.MainActivity.Companion.sound1
import com.example.polarmove.MainActivity.Companion.sound2
import com.example.polarmove.MainActivity.Companion.sound3
import com.example.polarmove.MainActivity.Companion.sound4
import com.example.polarmove.MainActivity.Companion.soundPool

class PlayerState(
    var xPos: Int = 0,
    var yPos: Int = 0,
    var zLevel: Int = 0,
    var keyframe: Int = 0,
    var walkCycle: ArrayList<ImageLoader.ImageWithName>,
    var jumpCycle: ArrayList<ImageLoader.ImageWithName>,
    var crawlCycle: ArrayList<ImageLoader.ImageWithName>,
    var gameVM: GameVM,
    var isJumping: Boolean = false,
    var isCrawling: Boolean = false,
    var playerPositionScale: Double = if ( deviceWidthInPixels > 1080 ) 0.44 else 0.60,
    val pos1: Int = ( playerPositionScale * deviceWidthInPixels - (distanceBetweenLines + deviceWidthInPixels * 0.2 + deviceWidthInPixels * 0.01)).toInt(),
    val pos2: Int = pos1 - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.02 ).toInt(),
    val pos3: Int = pos2 - (distanceBetweenLines + deviceWidthInPixels * 0.16 + deviceWidthInPixels * 0.02 ).toInt()
) {

    val image: ImageBitmap
        get() = if ( isJumping && keyframe <= 38 ) {
            jumpCycle[0].image
        } else if ( isJumping && keyframe > 38 ){
            jumpCycle[1].image
        } else if( isCrawling && keyframe <= 20) {
            crawlCycle[0].image
        } else if ( isCrawling && keyframe > 20){
            crawlCycle[1].image
        } else if ( keyframe <=10) {
            walkCycle[0].image
        } else {
            walkCycle[1].image
        }

    init {
        playerInit()
    }

    fun playerInit(){
        xPos = pos2
        yPos = (deviceHeightInPixels - (deviceHeightInPixels * 0.15 + distanceBetweenLines * 3)).toInt()
        zLevel = 1
        isJumping = false
        isCrawling = false
    }

    fun move() {
        changeKeyframe()
    }

    fun changeKeyframe() {
        keyframe++
        if (isJumping && keyframe == 76) {
            isJumping = false
            keyframe = 0
        } else if (!isJumping && !isCrawling && keyframe == 20) {
            keyframe = 0
        } else if (isCrawling && keyframe == 40) {
                keyframe = 0
        }
    }

    fun up(score: Int){
        if ( isCrawling ) {
            crawl(score)
        } else {
            jump(score)
        }
    }


    fun moveLeft(score: Int) {
        soundPool!!.play(sound1, 1f, 1f, 0, 0, 2f)
        xPos = if ( xPos == pos1 ) pos2 else pos3
        gameVM.dashIncrease(score)
    }

    fun moveRight(score: Int) {
        soundPool!!.play(sound1, 1f, 1f, 0, 0, 2f)
        xPos = if ( xPos == pos3 ) pos2 else pos1
        gameVM.dashIncrease(score)
    }

    fun jump(score: Int) {
        soundPool!!.play(sound2, 1f, 1f, 0, 0, .8f)
        soundPool!!.play(sound3, 1f, 1f, 0, 0, .8f)
        isCrawling = false
        isJumping = true
        keyframe = 0
        gameVM.jumpIncrease(score)
    }

    fun crawl(score: Int) {
        isCrawling = !isCrawling
        keyframe = 0
        gameVM.squatIncrease(score)
        soundPool!!.play(sound4, 1f, 1f, 0, 0, .8f)
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
