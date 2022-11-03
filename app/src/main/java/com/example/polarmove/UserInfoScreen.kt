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
fun UserInfo( navControl: NavController, userVM: UserVM ){

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        Text(text = "username: ${userVM.userData.username}")
        Text(text = "weight: ${userVM.userData.weight}")
        Text(text = "height: ${userVM.userData.height}")
        Text(text = "age: ${userVM.userData.age}")

        OutlinedButton(onClick = { navControl.navigate("MainScreen") }) {
            Text(text = "Main screen")
        }
    }

}