package com.example.polarmove

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginRegister( userVM: UserVM, auth: FirebaseAuth ){

    var email by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        OutlinedTextField(
            value = email,
            onValueChange = { email = it},
            label = { Text(text = "email")},
            singleLine = true
        )

        OutlinedTextField(
            value = password,
            label = { Text( text = "password")},
            onValueChange = { password = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Password)
        )

        OutlinedButton(
            onClick = { login(email, password, userVM, auth)}
        ){
            Text( text = "Login")
        }
    }
}