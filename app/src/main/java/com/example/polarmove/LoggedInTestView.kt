package com.example.polarmove

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoggedInTestView( userVM: UserVM, auth: FirebaseAuth ){

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        Text(text = "Logged In as: ${userVM.userEmail.value}")
        OutlinedButton(onClick = {userVM.signOut( auth )}) {
            Text(text = "Sign out")
        }
    }
}