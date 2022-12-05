package com.example.cat_runner

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay



@Composable
fun Ending() {

    val catsigh = remember { Animatable(initialValue = 200f) }

    LaunchedEffect(catsigh){
        catsigh.animateTo(
            targetValue = 220f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 3000, delayMillis = 0, easing = LinearEasing)
            )
        )
    }



    Image(
        painter = painterResource(id = R.drawable.endingscreencat),
        contentDescription = "Cat ending screen",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(600.dp)
            .offset(0.dp, 0.dp)
    )

    Image(
        painter = painterResource(id = R.drawable.endingscreencatsighcloud ),
        contentDescription = "House image",
        contentScale = ContentScale.Crop,
        modifier = Modifier.size((catsigh.value).dp).offset((290-catsigh.value).dp,(catsigh.value).dp)
    )
}

