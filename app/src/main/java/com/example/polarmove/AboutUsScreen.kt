package com.example.polarmove

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.polarmove.ui.theme.PolarRed

@Composable
fun AboutUs( navControl: NavController, userVM: UserVM ) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Box(
            modifier = Modifier
                .fillMaxSize(0.9f)
                .border(
                    BorderStroke(.8.dp, PolarRed),
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
                    Text(text = "P", style = MaterialTheme.typography.h1 )
                    Text(text = "O", color = PolarRed, style = MaterialTheme.typography.h1 )
                    Text(text = "LAR", style = MaterialTheme.typography.h1 )
                    Text(text = " MOVE", style = MaterialTheme.typography.h1 )
                }
                Spacer(modifier = Modifier.height(5.dp))
//                Text(
//                    text = stringResource(R.string.description_1),
//                    textAlign = TextAlign.Center
//                )
                Spacer(modifier = Modifier.height(50.dp))
//                Text(
//                    text = stringResource(R.string.description_2) + " " +
//                            stringResource(R.string.description_3),
//                    textAlign = TextAlign.Center
//                )
                Spacer(modifier = Modifier.height(5.dp))
//                Text(
//                    text = stringResource(R.string.description_4) + " " +
//                            stringResource(R.string.description_5),
//                    textAlign = TextAlign.Center
//                )
                Spacer(modifier = Modifier.height(50.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Authors", fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Bastian Walter",
                    fontStyle = FontStyle.Italic,
                    color = Color.Cyan,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Riku Tuisku",
                    fontStyle = FontStyle.Italic,
                    color = Color.Cyan,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Aleksandar Raynov",
                    fontStyle = FontStyle.Italic,
                    color = Color.Cyan,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    OutlinedButton(onClick = { navControl.navigate("MainScreen") }) {
                        Text(text = "Main screen")
                    }
                }

            }
        }
    }
}