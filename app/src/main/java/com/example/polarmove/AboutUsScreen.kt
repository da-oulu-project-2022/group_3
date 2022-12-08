package com.example.polarmove

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.polarmove.ui.theme.PolarRed

@Composable
fun AboutUs(
    navControl: NavController,
    scState: ScaffoldState
){
    Scaffold(
        scaffoldState = scState,
        topBar = { TopBar() },
        bottomBar = { BottomBar( navControl )},
        content = { AboutUsContent() }
    )
}

@Composable
fun AboutUsContent(){

    val annotedLink = buildAnnotatedString {
        val txt = "Project repository"
        append(txt)
        addStyle(
            style = SpanStyle(
                fontSize = 18.sp,
                color = Color.White,
                textDecoration = TextDecoration.Underline
            ), start = 0, end = txt.lastIndex + 1
        )
        addStringAnnotation(
            tag = "URL",
            annotation = "https://github.com/da-oulu-project-2022/group_3",
            start = 0,
            end = txt.lastIndex + 1
        )
    }

    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Box(
            modifier = Modifier
                .fillMaxHeight(0.82f)
                .fillMaxWidth((.9f))
                .border(
                    BorderStroke(.9.dp, PolarRed),
                    shape = MaterialTheme.shapes.large,
                )
                .padding(15.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(enabled = true, state = ScrollState(1))
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "P", fontSize = 25.sp )
                    Text(text = "O",fontSize = 25.sp, color = PolarRed )
                    Text(text = "LAR", fontSize = 25.sp )
                    Text(text = " MOVE", fontSize = 25.sp )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Collaboration project between students from" +
                            " University of applied sciences Darmstadt and University" +
                            " of applied sciences Oulu.",
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "The project is in association with Polar Electro OY," +
                            " which provided us with sensors and their SDK." +
                            " Our goal is to create a motion sensor controlled game" +
                            " and track the physical activity of the player during gameplay.",
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(5.dp))
//                Text(
//                    text = stringResource(R.string.description_4) + " " +
//                            stringResource(R.string.description_5),
//                    textAlign = TextAlign.Center
//                )
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Developers:", fontSize = 17.sp)
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Riku Tuisku",
                    fontStyle = FontStyle.Italic,
                    color = PolarRed,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Bastian Walter",
                    fontStyle = FontStyle.Italic,
                    color = PolarRed,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Aleksandar Raynov",
                    fontStyle = FontStyle.Italic,
                    color = PolarRed,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(30.dp))
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = "right arrow",
                        tint = PolarRed
                    )
                    ClickableText(
                        text = annotedLink,
                        onClick = {
                            annotedLink
                                .getStringAnnotations("URL", it, it)
                                .firstOrNull()?.let { stringAnnotation ->
                                    uriHandler.openUri(stringAnnotation.item)
                                }
                        }
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_right),
                        contentDescription = "right arrow",
                        tint = PolarRed
                    )
                }

            }
        }
    }
}