package com.example.polarmove

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.polarmove.ui.theme.PolarRed
import com.google.firebase.auth.FirebaseAuth
import java.util.*

@Composable
fun HighScores(
    navControl: NavController,
    scState: ScaffoldState,
    gameVM: GameVM,
    userVM: UserVM
){
    Scaffold(
        scaffoldState = scState,
        topBar = { TopBar() },
        bottomBar = { BottomBar(navControl) },
        content = { HighScoresContent( gameVM, userVM ) }
    )
}

@Composable
fun HighScoresContent( gameVM: GameVM, userVM: UserVM ){

    val highScoreGames = gameVM.highScoreGames.value

    Log.d("CALORIES: ", "${gameVM.calories.value}")

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Row(
            horizontalArrangement = Arrangement.Center
        ){
            Text( "H", color = PolarRed, style = MaterialTheme.typography.h1, fontSize = 12.sp )
            Text( "IGH ", style = MaterialTheme.typography.h1, fontSize = 12.sp )
            Text( "SCORE", style = MaterialTheme.typography.h1, fontSize = 12.sp )
            Text( "S", color = PolarRed, style = MaterialTheme.typography.h1, fontSize = 12.sp )
        }
        Spacer(modifier = Modifier.height(25.dp))

        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxSize(.86f)
                .verticalScroll(enabled = true, state = ScrollState(1)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            highScoreGames.forEach { game ->

                OutlinedButton(
                    onClick = {  },
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
                    Text(text = game.player, color = Color.White)
                    Spacer(modifier = Modifier.width(120.dp))
                    Text(text = game.score.toString())
                }

                Spacer(modifier = Modifier.height(10.dp))

            }

            OutlinedButton(
                onClick = { gameVM.saveGame(110,128, userVM.userEmail.value, userVM.userName.value, userVM.userData.weight.toInt(), userVM.userData.age.toInt(), userVM.userData.gender ) },
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
                Text(text = "Save new game", color = Color.White)
                Spacer(modifier = Modifier.width(120.dp))
//                Text(text = game.score.toString())
            }
        }
    }




}