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

    var age by remember { mutableStateOf(userVM.userData.age.toString()) }
    val ageRange = (1..100).toList()

    var height by remember { mutableStateOf(userVM.userData.height.toString())}
    val heightRange = (90..220).toList()

    var weight by remember { mutableStateOf(userVM.userData.weight.toString())}
    val weightRange = (30..150).toList()

    var gender by remember { mutableStateOf(userVM.userData.gender)}
    val genders = listOf("Male", "Female")

    var ageExpanded by remember { mutableStateOf(false)}
    var heightExpanded by remember { mutableStateOf(false)}
    var weightExpanded by remember { mutableStateOf(false)}
    var genderExpanded by remember { mutableStateOf(false)}

    var saveButton by remember { mutableStateOf(false)}

    saveButton = age.toInt() != userVM.userData.age.toInt() ||
            weight.toInt() != userVM.userData.weight.toInt() ||
            height.toInt() != userVM.userData.height.toInt() ||
            gender != userVM.userData.gender

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

        if(saveButton){
            OutlinedButton(
                onClick = {
                    userVM.changeStats(weight.toInt(), height.toInt(), gender, age.toInt() )
                    Log.d("user", userVM.userData.toString())
                }
            )
            {
                    Text(text = "Save info")
            }
        }

    }

}