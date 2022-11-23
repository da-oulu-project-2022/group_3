package com.example.polarmove

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.polarmove.ui.theme.PolarRed

@Composable
fun SavedGameScreen(
    navControl: NavController,
    scState: ScaffoldState,
    game: GameDataClass
){
    Scaffold(
        scaffoldState = scState,
        topBar = { TopBar() },
        bottomBar = { BottomBar( navControl )},
        content = { SavedGameContent( game, navControl ) }
    )
}

@Composable
fun SavedGameContent(
    game: GameDataClass,
    navControl: NavController
){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        //Upper half of the screen holding the user stats
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.4f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(60.dp))

            Row(
                horizontalArrangement = Arrangement.Center
            ){
                Text( "G", color = PolarRed, style = MaterialTheme.typography.h1, fontSize = 12.sp )
                Text( "AME ", style = MaterialTheme.typography.h1, fontSize = 12.sp )
                Text( "INF", style = MaterialTheme.typography.h1, fontSize = 12.sp )
                Text( "O", color = PolarRed, style = MaterialTheme.typography.h1, fontSize = 12.sp )
            }
            Spacer(modifier = Modifier.height(40.dp))

            //User stats
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){

                //Left column holding the stats description
                Column(
                    modifier = Modifier
                        .fillMaxWidth(.2f),
                    horizontalAlignment = Alignment.Start
                ){
                    Row {
                        Text(
                            "S",
                            color = PolarRed,
                            style = MaterialTheme.typography.h1,
                            fontSize = 12.sp)
                        Text(
                            "CORE",
                            style = MaterialTheme.typography.h1,
                            fontSize = 12.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        Text(
                            "SQUAT",
                            style = MaterialTheme.typography.h1,
                            fontSize = 12.sp
                        )
                        Text("S",
                            color = PolarRed,
                            style = MaterialTheme.typography.h1,
                            fontSize = 12.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        Text(
                            "J",
                            color = PolarRed,
                            style = MaterialTheme.typography.h1,
                            fontSize = 12.sp
                        )
                        Text(
                            "UMPS",
                            style = MaterialTheme.typography.h1,
                            fontSize = 12.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        Text(
                            "DASHE",

                            style = MaterialTheme.typography.h1,
                            fontSize = 12.sp
                        )
                        Text(
                            "S",
                            color = PolarRed,
                            style = MaterialTheme.typography.h1,
                            fontSize = 12.sp
                        )
                    }
                }

                //Right column holding the actual user stats
                Column(
                    modifier = Modifier
                        .fillMaxWidth(.3f),
                    horizontalAlignment = Alignment.End
                ){
                    Text( text = "${game.score}")
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "${game.squats}")
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "${game.jumps}")
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "${game.dashes}")
                }
            }
//            Spacer(modifier = Modifier.height(70.dp))

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(75.dp))

            Row(
                horizontalArrangement = Arrangement.Center
            ){
                Text( "H", color = PolarRed, style = MaterialTheme.typography.h1, fontSize = 12.sp )
                Text( "EALTH ", style = MaterialTheme.typography.h1, fontSize = 12.sp )
                Text( "INF", style = MaterialTheme.typography.h1, fontSize = 12.sp )
                Text( "O", color = PolarRed, style = MaterialTheme.typography.h1, fontSize = 12.sp )
            }
            Spacer(modifier = Modifier.height(40.dp))

            //User stats
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){

                //Left column holding the stats description
                Column(
                    modifier = Modifier
                        .fillMaxWidth(.3f),
                    horizontalAlignment = Alignment.Start
                ){
                    Row {
                        Text(
                            "C",
                            color = PolarRed,
                            style = MaterialTheme.typography.h1,
                            fontSize = 12.sp)
                        Text(
                            "ALORIES",
                            style = MaterialTheme.typography.h1,
                            fontSize = 12.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        Text(
                            "HR-MA",
                            style = MaterialTheme.typography.h1,
                            fontSize = 12.sp
                        )
                        Text("X",
                            color = PolarRed,
                            style = MaterialTheme.typography.h1,
                            fontSize = 12.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        Text(
                            "H",
                            color = PolarRed,
                            style = MaterialTheme.typography.h1,
                            fontSize = 12.sp
                        )
                        Text(
                            "R-AVG",
                            style = MaterialTheme.typography.h1,
                            fontSize = 12.sp
                        )
                    }
                }

                //Right column holding the actual user stats
                Column(
                    modifier = Modifier
                        .fillMaxWidth(.3f),
                    horizontalAlignment = Alignment.End
                ){
                    Text( text = "${game.calories}")
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "${game.heart_rate_max}")
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "${game.heart_rate_avg}")
                }
            }
            Spacer(modifier = Modifier.height(89.dp))

            OutlinedButton(
                onClick = { navControl.navigate("GameHistory") },
                modifier = Modifier
                    .fillMaxWidth(.75f)
                    .height(40.dp),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colors.onPrimary,
                    backgroundColor = MaterialTheme.colors.surface
                ),
                elevation = ButtonDefaults.elevation(2.dp, 2.dp, 0.dp)
            ) {
                Text(text = "Back")
            }

        }
    }
}