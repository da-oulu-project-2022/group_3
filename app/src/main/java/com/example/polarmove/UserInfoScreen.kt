package com.example.polarmove

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun UserInfo( navControl: NavController, userVM: UserVM ){

    var userData by remember { mutableStateOf(userVM.userData)}

    var age by remember { mutableStateOf(userData.age.toString()) }
    val ageRange = (1..100).toList()

    var height by remember { mutableStateOf(userData.height.toString())}
    val heightRange = (90..220).toList()

    var weight by remember { mutableStateOf(userData.weight.toString())}
    val weightRange = (30..150).toList()

    var gender by remember { mutableStateOf(userData.gender)}
    val genders = listOf("Male", "Female")

    //Drop down menu booleans
    var ageExpanded by remember { mutableStateOf(false)}
    var heightExpanded by remember { mutableStateOf(false)}
    var weightExpanded by remember { mutableStateOf(false)}
    var genderExpanded by remember { mutableStateOf(false)}

    var saveButton by remember { mutableStateOf(false)}

    //Visibility of the save button if stats are changed
    saveButton = age.toInt() != userData.age.toInt() ||
            weight.toInt() != userData.weight.toInt() ||
            height.toInt() != userData.height.toInt() ||
            gender != userData.gender

    Column(
        modifier = Modifier.fillMaxSize()
    ){

        Text(text = "username: ${userData.username}")
        Text(text = "weight: ${userData.weight}")
        Text(text = "height: ${userData.height}")
        Text(text = "age: ${userData.age}")

        OutlinedButton(onClick = { navControl.navigate("MainScreen") }) {
            Text(text = "Main screen")
        }

        //Age input field and dropdown menu
        Column(){
            OutlinedTextField(
                value = "$age y",
                label = { Text( text = "Age")},
                onValueChange = { age = it },
                singleLine = true,
                enabled = false,
                keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Number ),
                modifier = Modifier
                    .fillMaxWidth(.32f)
                    .clickable { ageExpanded = !ageExpanded },
                trailingIcon = {
                    Icon( painter = painterResource(R.drawable.dropdown_arrow), "description" )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledBorderColor = Color.Cyan,
                    disabledLabelColor = Color.Cyan,
                    disabledTextColor = Color.Cyan,
                    disabledTrailingIconColor = Color.Cyan
                )
            )
            DropdownMenu(
                expanded = ageExpanded,
                onDismissRequest = { ageExpanded = false },
                modifier = Modifier
                    .fillMaxWidth(.32f)
                    .height(300.dp)
            ) {
                ageRange.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            age = option.toString()
                            ageExpanded = false
                        }
                    ) {
                        Text(text = option.toString())
                    }
                }
            }
        }

        //Height input field and dropdown menu
        Column(){
            OutlinedTextField(
                value = "$height cm",
                label = { Text( text = "Height")},
                onValueChange = { height = it },
                singleLine = true,
                enabled = false,
                keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Number ),
                modifier = Modifier
                    .fillMaxWidth(.32f)
                    .clickable { heightExpanded = !heightExpanded },
                trailingIcon = {
                    Icon( painter = painterResource(R.drawable.dropdown_arrow), "description" )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledBorderColor = Color.Cyan,
                    disabledLabelColor = Color.Cyan,
                    disabledTextColor = Color.Cyan,
                    disabledTrailingIconColor = Color.Cyan
                )
            )
            DropdownMenu(
                    expanded = heightExpanded,
            onDismissRequest = { heightExpanded = false },
            modifier = Modifier
                .fillMaxWidth(.32f)
                .height(300.dp)
            ) {
                heightRange.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            height = option.toString()
                            heightExpanded = false
                        }
                    ) {
                        Text(text = option.toString())
                    }
                }
            }
        }

        //Weight input field and dropdown menu
        Column(){
            OutlinedTextField(
                value = "$weight kg",
                label = { Text( text = "Weight")},
                onValueChange = { weight = it },
                singleLine = true,
                enabled = false,
                keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Number ),
                modifier = Modifier
                    .fillMaxWidth(.32f)
                    .clickable { weightExpanded = !weightExpanded },
                trailingIcon = {
                    Icon( painter = painterResource(R.drawable.dropdown_arrow), "description" )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledBorderColor = Color.Cyan,
                    disabledLabelColor = Color.Cyan,
                    disabledTextColor = Color.Cyan,
                    disabledTrailingIconColor = Color.Cyan
                )
            )
            DropdownMenu(
                expanded = weightExpanded,
                onDismissRequest = { weightExpanded = false },
                modifier = Modifier
                    .fillMaxWidth(.32f)
                    .height(300.dp)
            ) {
                weightRange.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            weight = option.toString()
                            weightExpanded = false
                        }
                    ) {
                        Text(text = option.toString())
                    }
                }
            }
        }

        //Gender input field and dropdown menu
        Column() {
            OutlinedTextField(
                value = gender,
                label = { Text( text = "Gender")},
                onValueChange = { gender = it },
                singleLine = true,
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth(.32f)
                    .clickable { genderExpanded = !genderExpanded },
                trailingIcon = {
                    Icon( painter = painterResource(R.drawable.dropdown_arrow), "description" )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledBorderColor = Color.Cyan,
                    disabledLabelColor = Color.Cyan,
                    disabledTextColor = Color.Cyan,
                    disabledTrailingIconColor = Color.Cyan
                )
            )
            DropdownMenu(
                expanded = genderExpanded,
                onDismissRequest = { genderExpanded = false },
                modifier = Modifier
                    .fillMaxWidth(.32f)
            ) {
                genders.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            gender = option
                            genderExpanded = false
                        }
                    ) {
                        Text(text = option)
                    }
                }
            }
        }

        //Save button if stats are changed
        if(saveButton){
            OutlinedButton(
                onClick = {
                    val newUser = UserDataClass(userData.username, userData.email, weight.toInt(), height.toInt(), gender, age.toInt() )
                    userVM.changeStats( newUser )
                    userData = newUser
                    Log.d("userVM", userVM.userData.toString())
                }
            )
            {
                Text(text = "Save info")
            }
        }

    }

}