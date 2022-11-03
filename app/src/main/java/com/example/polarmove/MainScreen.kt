package com.example.polarmove

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainScreen( navControl: NavController, userVM: UserVM, auth: FirebaseAuth ){

    Column(
        modifier = Modifier.fillMaxSize()
    ){

        Text(text = "Logged In as: ${userVM.userEmail.value}")
        OutlinedButton(onClick = { navControl.navigate("GameScreen") }) {
            Text(text = "New game")
        }
        OutlinedButton(onClick = { navControl.navigate("UserInfo") }) {
            Text(text = "User info")
        }
        OutlinedButton(onClick = { navControl.navigate("HighScores") }) {
            Text(text = "High scores")
        }
        OutlinedButton(onClick = { navControl.navigate("GameHistory") }) {
            Text(text = "Game history")
        }
        OutlinedButton(onClick = { navControl.navigate("AboutUs") }) {
            Text(text = "About us")
        }
        OutlinedButton(onClick = {userVM.signOut( auth )}) {
            Text(text = "Sign out")
        }

    }

}