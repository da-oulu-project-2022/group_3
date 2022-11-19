package com.example.cat_runner

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp



@Composable
fun LaneMovement() {
    val animLane3 = remember { Animatable(initialValue = -300f) }
    val animLane2 = remember { Animatable(initialValue = -300f) }
    val animLane1 = remember { Animatable(initialValue = -300f) }
    Lines()

    //Fast pace 500f, duration 1000
    //Normal pace 500f, duration 2000
    //Slow pace 500f, duration 3000
    //Third lane settings
    LaunchedEffect(animLane3){
        animLane3.animateTo(
            targetValue = 500f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 3000, delayMillis = 0, easing = LinearEasing)
            )
        )
    }
    //Second lane settings
    LaunchedEffect(animLane2){
        animLane2.animateTo(
            targetValue = 500f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 3000, delayMillis = 0, easing = LinearEasing)
            )
        )
    }
    //First lane settings
    LaunchedEffect(animLane1){
        animLane1.animateTo(
            targetValue = 500f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 3000, delayMillis = 0, easing = LinearEasing)
            )
        )
    }

    //Brick movement starts here
    //Third lane
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane3.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(1000f, -1400f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane3.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(950f, -1200f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane3.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(1000f, -1000f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane3.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(950f, -800f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane3.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(1000f, -600f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane3.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(950f, -400f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane3.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(1000f, -200f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane3.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(950f, -0f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane3.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(1000f, 200f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane3.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(950f, 400f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane3.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(1000f, 600f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane3.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(950f, 800f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane3.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(1000f, 1000f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane3.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(950f, 1200f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane3.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(1000f, 1400f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane3.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(950f, 1600f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane3.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(1000f, 1800f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane3.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(950f, 2000f),
                size = Size(60f,5f),
            )
        }
    })
    //End of third lane
    //-----------------------------------------------------
    //Second lane
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane2.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(778f, -1400f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane2.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(850f, -1200f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane2.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(778f, -1000f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane2.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(850f, -800f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane2.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(778f, -600f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane2.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(850f, -400f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane2.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(778f, -200f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane2.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(850f, -0f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane2.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(778f, 200f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane2.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(850f, 400f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane2.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(778f, 600f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane2.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(850f, 800f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane2.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(778f, 1000f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane2.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(850f, 1200f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane2.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(778f, 1400f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane2.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(850f, 1600f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane2.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(778f, 1800f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane2.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(850f, 2000f),
                size = Size(60f,5f),
            )
        }
    })
    //End of second lane
    //-----------------------------------------------------
    //First lane
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane1.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(680f, -1400f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane1.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(625f, -1200f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane1.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(680f, -1000f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane1.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(625f, -800f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane1.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(680f, -600f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane1.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(625f, -400f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane1.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(680f, -200f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane1.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(625f, -0f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane1.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(680f, 200f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane1.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(625f, 400f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane1.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(680f, 600f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane1.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(625f, 800f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane1.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(680f, 1000f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane1.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(625f, 1200f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane1.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(680f, 1400f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane1.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(625f, 1600f),
                size = Size(60f,5f),
            )
        }
    })
    //-----------------------------------------------------
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane1.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(680f, 1800f),
                size = Size(60f,5f),
            )
        }
    })

    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane1.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(625f, 2000f),
                size = Size(60f,5f),
            )
        }
    })
    //End of First lane
    //-----------------------------------------------------
}

@Composable
fun Lines() {
    Column(
        modifier = Modifier
            .width(500.dp)
            .fillMaxHeight()
            .absolutePadding(right = 5.dp),
        horizontalAlignment = Alignment.End
    )
    {
        Row() {
            repeat(3) {
                Spacer(Modifier.padding(3.dp))
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .fillMaxHeight()
                        .background(color = Color.LightGray)
                )
            }
        }
    }
}