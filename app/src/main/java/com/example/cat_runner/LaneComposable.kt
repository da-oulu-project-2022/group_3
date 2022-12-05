package com.example.cat_runner

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@Composable
fun LaneMovement() {
    val animLane = remember { Animatable(initialValue = -300f) }

    Lines()
    ScoreBox()
    //Fast pace 500f, duration 1500
    //Normal pace 500f, duration 3000
    //Slow pace 500f, duration 5000
    LaunchedEffect(animLane){
        animLane.animateTo(
            targetValue = 500f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 5000, delayMillis = 0, easing = LinearEasing)
            )
        )
    }


    //lane starts here
    Canvas(modifier = Modifier.size(100.dp), onDraw = {
        withTransform({
            translate(top = animLane.value)
        }) {

            drawRect(color = Color.Gray,
                topLeft = Offset(532f, -600f),
                size = Size(505f,10f),
            )

            drawRect(color = Color.Gray,
                topLeft = Offset(532f, -400f),
                size = Size(505f,10f),
            )

            drawRect(color = Color.Gray,
                topLeft = Offset(532f, -200f),
                size = Size(505f,10f),
            )

            drawRect(color = Color.Gray,
                topLeft = Offset(532f, 0f),
                size = Size(505f,10f),
            )

            drawRect(color = Color.Gray,
                topLeft = Offset(532f, 200f),
                size = Size(505f,10f),
            )

            drawRect(color = Color.Gray,
                topLeft = Offset(532f, 400f),
                size = Size(505f,10f),
            )

            drawRect(color = Color.Gray,
                topLeft = Offset(532f, 600f),
                size = Size(505f,10f),
            )

            drawRect(color = Color.Gray,
                topLeft = Offset(532f, 800f),
                size = Size(505f,10f),
            )

            drawRect(color = Color.Gray,
                topLeft = Offset(532f, 1000f),
                size = Size(505f,10f),
            )

            drawRect(color = Color.Gray,
                topLeft = Offset(532f, 1200f),
                size = Size(505f,10f),
            )

            drawRect(color = Color.Gray,
                topLeft = Offset(532f, 1400f),
                size = Size(505f,10f),
            )

            drawRect(color = Color.Gray,
                topLeft = Offset(532f, 1600f),
                size = Size(505f,10f),
            )

            drawRect(color = Color.Gray,
                topLeft = Offset(532f, 1800f),
                size = Size(505f,10f),
            )

            drawRect(color = Color.Gray,
                topLeft = Offset(532f, 2000f),
                size = Size(505f,10f),
            )

            drawRect(color = Color.Gray,
                topLeft = Offset(532f, 2200f),
                size = Size(505f,10f),
            )

        }
    })

}

@Composable
fun Lines() {
    Column(
        modifier = Modifier
            .width(500.dp)
            .fillMaxHeight()
            .absolutePadding(right = 15.dp),
        horizontalAlignment = Alignment.End
    )
    {
        Row {
            repeat(3) {
                Spacer(Modifier.padding(1.dp))
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .fillMaxHeight()
                        .background(color = Color.LightGray)

                )
            }
        }
    }
}