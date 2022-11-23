package com.example.polarmove

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
import java.util.*


@Composable
fun GameHistory(
    navControl: NavController,
    userVM: UserVM,
    gameVM: GameVM,
    scState: ScaffoldState
){
    Scaffold(
        scaffoldState = scState,
        topBar = { TopBar() },
        bottomBar = { BottomBar( navControl )},
        content = { GameHistoryContent( navControl, userVM, gameVM )}
    )
}

@Composable
fun GameHistoryContent( navControl: NavController, userVM: UserVM, gameVM: GameVM ){

    val ownGames = gameVM.ownGames.value

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Row(
            horizontalArrangement = Arrangement.Center
        ){
            Text( "P", color = PolarRed, style = MaterialTheme.typography.h1, fontSize = 12.sp )
            Text( "LAYED ", style = MaterialTheme.typography.h1, fontSize = 12.sp )
            Text( "GAME", style = MaterialTheme.typography.h1, fontSize = 12.sp )
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
            if( ownGames.isEmpty() ){
                Text(text = "You don't have any saved games")
            } else {
                ownGames.forEach { game ->

                    val date = Date(game.timestamp.toLong()).toString()
                    val dateSplit = date.split(" ")
                    val dateString = "${dateSplit[2]} ${dateSplit[1]} ${dateSplit[5]}"

                    OutlinedButton(
                        onClick = { navControl.navigate( game.timestamp.toString()) },
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
                        Text(text = dateString, color = Color.White)
                        Spacer(modifier = Modifier.width(120.dp))
                        Text(text = game.score.toString())
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                }
            }
        }


    }

}