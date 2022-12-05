package com.example.cat_runner

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun House() {

    //Fast pace 800f, duration 5600
    //Normal pace 800f, duration 11600
    //Slow pace 800f, duration 19000

    val animMove = remember { Animatable(initialValue = -300f) }

    LaunchedEffect(animMove){
        animMove.animateTo(
            targetValue = 800f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 19000, delayMillis = 0, easing = LinearEasing)
            )
        )
    }

    Image(
        painter = painterResource(id = R.drawable.house ),
        contentDescription = "House image",
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(200.dp).offset((-50).dp,(animMove.value).dp)
    )}

