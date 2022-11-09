package com.example.polarmove

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun GameHistory( navControl: NavController, userVM: UserVM, gameVM: GameVM ){

    val ownGames = gameVM.ownGames.value

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        OutlinedButton(onClick = { navControl.navigate("MainScreen") }) {
            Text(text = "Main screen")
        }
        if( ownGames.isEmpty() ){
            Text(text = "You don't have any saved games")
        } else {
            ownGames.forEach { game ->
                Text(text = "Username: ${game.username}")
                Text(text = "Game score: ${game.score}")
            }
        }
    }

}