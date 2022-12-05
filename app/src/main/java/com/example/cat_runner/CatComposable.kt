package com.example.cat_runner

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@Composable
 fun CatWalk() {
    //Change delay accordingly to speed in millis delay and add 20 millis difference for smoother walk cycle
    //Fast pace duration 170
    //Normal pace  duration 350
    //Slow pace duration 1000
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
                painter = painterResource(id = R.drawable.catwalkcycle),
                contentDescription = "Cat",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .offset(190.dp, 500.dp)
            )
        }
        if (!visible){

            Image(
                painter = painterResource(id = R.drawable.catwalkcycle2),
                contentDescription = "Cat",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .offset(190.dp, 500.dp)
            )
        }
 }

@Composable
fun CatCrouch() {

    //Change delay accordingly to speed in millis delay and add 20 millis difference for smoother walk cycle
    //Fast pace duration 170
    //Normal pace  duration 350
    //Slow pace duration 1000
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
            painter = painterResource(id = R.drawable.catcrouchcycle),
            contentDescription = "Cat",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(250.dp)
                .offset(165.dp, 480.dp)
        )
    }
    if (!visible){

        Image(
            painter = painterResource(id = R.drawable.catcrouchcycle2),
            contentDescription = "Cat",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(250.dp)
                .offset(165.dp, 480.dp)
        )
    }
}

@Composable
fun CatJump() {
    //Fast pace duration 170
    //Normal pace  duration 350
    //Slow pace duration 1500 & 1300
    var visible by remember { mutableStateOf(true) }
    LaunchedEffect(Boolean) {
        while(true) {
            delay(1500)
            visible = false
            delay(1300)
            visible = true

        }
    }

    if (visible) {
        Image(
            painter = painterResource(id = R.drawable.catjump),
            contentDescription = "Cat",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(330.dp)
                .offset(130.dp, 380.dp)
        )
    }

    if (!visible){

        Image(
            painter = painterResource(id = R.drawable.catjumplanding),
            contentDescription = "Cat",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(220.dp)
                .offset(180.dp, 480.dp)
        )
    }

}