package com.example.cat_runner

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ScoreBox() {


    var score by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        while(true) {
            delay(1000)
            score++
        }
    }

    Row(
        modifier = Modifier
            .width(199.dp)
            .height(40.dp)
            .offset((-5).dp, 696.dp)
            .clip(shape = RoundedCornerShape(topEnd = 10.dp))
            .background(color = Color.Gray)
            .border(BorderStroke(2.dp, Color.DarkGray))
        ,horizontalArrangement = Arrangement.SpaceBetween
        ,verticalAlignment = Alignment.CenterVertically
    )
    {
        Spacer(Modifier.padding(start = 2.dp))
        Text(text = "Name: Riku ")
        Text(text = "Score: $score")
        Spacer(Modifier.padding(end = 2.dp))
    }

}

@Composable
fun Health() {
    ScoreBox()
    Heartbeat()

    Row(
        modifier = Modifier
            .width(105.dp)
            .height(98.dp)
            .offset((-5).dp, 600.dp)
            .clip(shape = RoundedCornerShape(topEnd = 10.dp))
            .background(color = Color.Gray)
            .border(BorderStroke(2.dp, Color.DarkGray))
    ) {

    }
    Image(
        painter = painterResource(id = R.drawable.cathealthfull),
        contentDescription = "Cat",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(200.dp)
            .offset((-46).dp, 585.dp)
    )

}

@Composable
fun Heartbeat() {


    var visible by remember { mutableStateOf(true) }
    LaunchedEffect(Boolean) {
        while(true) {
            delay(300)
            visible = false
            delay(280)
            visible = true
        }
    }
    if (visible) {
        Image(
            painter = painterResource(id = R.drawable.heart),
            contentDescription = "Cat",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(105.dp)
                .offset(86.dp, 585.dp)
        )
    }
    if (!visible){

        Image(
            painter = painterResource(id = R.drawable.heart),
            contentDescription = "Cat",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(135.dp)
                .offset(83.dp, 575.dp)
        )
    }

}