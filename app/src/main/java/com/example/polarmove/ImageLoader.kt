package com.example.polarmove

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext

class ImageLoader {
    constructor(
        context: Context
    ){
        _context = context
    }
    private val _context: Context

    val imagesWithName: ArrayList<ImageWithName> = ArrayList()

    fun addImage(filname: String, scale: Int, imageResource: Int){
        imagesWithName.add(ImageWithName(filname, _context, scale, imageResource ))
    }

    class ImageWithName{
        val image: ImageBitmap
        val name: String
        constructor( filename: String, context: Context, scale: Int, imageResource: Int ){
            name = filename
            val file = BitmapFactory.decodeResource( context.resources, imageResource )
            image = Bitmap.createScaledBitmap( file, scale, scale, true ).asImageBitmap()
        }
    }


}