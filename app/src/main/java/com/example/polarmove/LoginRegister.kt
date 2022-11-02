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
    var repeatPassword by remember { mutableStateOf("")}
    var username by remember { mutableStateOf("")}
    var registerSwitch by remember { mutableStateOf(false)}

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        OutlinedTextField(
            value = email,
            onValueChange = { email = it},
            label = { Text(text = "email")},
            singleLine = true
        )

        if(registerSwitch){
            OutlinedTextField(
                value = username,
                onValueChange = { username = it},
                label = { Text(text = "username")},
                singleLine = true
            )
            OutlinedTextField(
                value = password,
                label = { Text( text = "password")},
                onValueChange = { password = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Password)
            )
            OutlinedTextField(
                value = repeatPassword,
                label = { Text( text = "repeat password")},
                onValueChange = { repeatPassword = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Password)
            )
        } else {
            OutlinedTextField(
                value = password,
                label = { Text( text = "password")},
                onValueChange = { password = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Password)
            )
        }

        if(!registerSwitch){
            OutlinedButton(
                onClick = { login(email, password, userVM, auth) }
            ){
                Text( text = "Login")
            }
            OutlinedButton(
                onClick = { registerSwitch = !registerSwitch }
            ){
                Text( text = "New user")
            }
        } else {
            OutlinedButton(
                onClick = { register(email, password, username, userVM, auth) }
            ){
                Text( text = "Register")
            }
            OutlinedButton(
                onClick = { registerSwitch = !registerSwitch }
            ){
                Text( text = "Registered? Sign in")
            }
        }

    }
}