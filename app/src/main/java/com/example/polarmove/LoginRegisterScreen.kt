package com.example.polarmove

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.polarmove.ui.theme.PolarRed
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginRegister( userVM: UserVM, auth: FirebaseAuth ){

    var email by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}
    var repeatPassword by remember { mutableStateOf("")}
    var username by remember { mutableStateOf("")}

    //Boolean to add or remove input fields based on the wanted action
    var registerSwitch by remember { mutableStateOf(false)}

    var showPassword by remember { mutableStateOf(false)}
    var showRepeatPassword by remember { mutableStateOf(false)}

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Spacer(modifier = Modifier.height(20.dp))
        TopBar()
        Spacer(modifier = Modifier.height(20.dp))

        //Username field for register
        if(registerSwitch) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(text = "username") },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PolarRed,
                    cursorColor = PolarRed,
                    textColor = PolarRed,
                    focusedLabelColor = PolarRed
                ),
                modifier = Modifier
                    .fillMaxWidth(.75f)
                    .height(64.dp),
                shape = MaterialTheme.shapes.large,
                keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Password)
            )
        }

        //Email feel for login and register
        OutlinedTextField(
            value = email,
            onValueChange = { email = it},
            label = { Text(text = "email")},
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.email_icon),
                    contentDescription = "mail icon",
                    Modifier
                        .padding(15.dp)
                        .size(18.dp)
                )
            },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = PolarRed,
                cursorColor = PolarRed,
                textColor = PolarRed,
                focusedLabelColor = PolarRed
            ),
            modifier = Modifier
                .fillMaxWidth(.75f)
                .height(64.dp),
            shape = MaterialTheme.shapes.large,
            keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Password)
        )

        //Password field for login and register
        OutlinedTextField(
            value = password,
            label = { Text( text = "password")},
            onValueChange = { password = it },
            trailingIcon = {
                Icon(
                    painter = if (showPassword) painterResource(R.drawable.visibility_off) else painterResource(R.drawable.visibility_on),
                    contentDescription = "eye password",
                    Modifier
                        .padding(15.dp)
                        .clickable { showPassword = !showPassword }
                        .size(18.dp)
                )
            },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = PolarRed,
                cursorColor = PolarRed,
                textColor = PolarRed,
                focusedLabelColor = PolarRed
            ),
            modifier = Modifier
                .fillMaxWidth(.75f)
                .height(64.dp),
            shape = MaterialTheme.shapes.large,
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Password)
        )

        //Repeat password field for register
        if (registerSwitch){
            OutlinedTextField(
                value = repeatPassword,
                label = { Text( text = "repeat password")},
                onValueChange = { repeatPassword = it },
                singleLine = true,
                trailingIcon = {
                    Icon(
                        painter = if (showRepeatPassword) painterResource(R.drawable.visibility_off) else painterResource(R.drawable.visibility_on),
                        contentDescription = "eye password",
                        Modifier
                            .padding(15.dp)
                            .clickable { showRepeatPassword = !showRepeatPassword }
                            .size(18.dp)
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PolarRed,
                    cursorColor = PolarRed,
                    textColor = PolarRed,
                    focusedLabelColor = PolarRed
                ),
                modifier = Modifier
                    .fillMaxWidth(.75f)
                    .height(64.dp),
                shape = MaterialTheme.shapes.large,
                visualTransformation = if (showRepeatPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Password)
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        //Login button
        if(!registerSwitch){
            OutlinedButton(
                onClick = { login(email, password, userVM, auth) },
                modifier = Modifier
                    .fillMaxWidth(.75f)
                    .height(40.dp),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colors.onPrimary,
                    backgroundColor = MaterialTheme.colors.surface
                ),
                elevation = ButtonDefaults.elevation(2.dp, 2.dp, 0.dp)
            ){
                Text( text = "Login")
            }

            Spacer(modifier = Modifier.height(5.dp))

            //Switch to register mode
            OutlinedButton(
                onClick = {
                    registerSwitch = !registerSwitch
                    email = ""
                    password = ""
                    repeatPassword = ""
                    username = ""
                },
                modifier = Modifier
                    .fillMaxWidth(.75f)
                    .height(40.dp),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                elevation = ButtonDefaults.elevation(2.dp, 2.dp, 0.dp)
            ){
                Text( text = "New user")
            }
        } else {

            //Register button
            OutlinedButton(
                onClick = { register(email, password, username, userVM, auth) },
                modifier = Modifier
                    .fillMaxWidth(.75f)
                    .height(40.dp),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colors.onPrimary,
                    backgroundColor = MaterialTheme.colors.surface
                ),
                elevation = ButtonDefaults.elevation(2.dp, 2.dp, 0.dp)
            ){
                Text( text = "Register")
            }

            Spacer(modifier = Modifier.height(5.dp))

            //Switch to login mode
            OutlinedButton(
                onClick = {
                    registerSwitch = !registerSwitch
                    email = ""
                    password = ""
                },
                modifier = Modifier
                    .fillMaxWidth(.75f)
                    .height(40.dp),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.elevation(2.dp, 2.dp, 0.dp)
            ){
                Text( text = "Registered? Sign in")
            }
        }
    }
}