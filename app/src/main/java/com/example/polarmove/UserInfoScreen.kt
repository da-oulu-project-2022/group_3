package com.example.polarmove

import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.polarmove.ui.theme.PolarRed

@Composable
fun UserInfo(
    navControl: NavController,
    userVM: UserVM,
    scState: ScaffoldState
){
    Scaffold(
        scaffoldState = scState,
        topBar = { TopBar() },
        bottomBar = { BottomBar( navControl )},
        content = { UserInfoContent( userVM )}
    )
}

@Composable
fun UserInfoContent( userVM: UserVM ){

    var userData by remember { mutableStateOf(userVM.userData)}

    var age by remember { mutableStateOf(userData.age.toString()) }
    val ageRange = (1..100).toList()

    var height by remember { mutableStateOf(userData.height.toString())}
    val heightRange = (90..220).toList()

    var weight by remember { mutableStateOf(userData.weight.toString())}
    val weightRange = (30..150).toList()

    var gender by remember { mutableStateOf(userData.gender)}
    val genders = listOf("Male", "Female")

    val highscore by remember { mutableStateOf(userData.highscore)}

    //Drop down menu booleans
    var ageExpanded by remember { mutableStateOf(false)}
    var heightExpanded by remember { mutableStateOf(false)}
    var weightExpanded by remember { mutableStateOf(false)}
    var genderExpanded by remember { mutableStateOf(false)}

    var changeStatsVisibility by remember { mutableStateOf(false)}
    var saveButton by remember { mutableStateOf(false)}

    //Visibility of the save button if stats are changed
    saveButton = age.toInt() != userData.age.toInt() ||
            weight.toInt() != userData.weight.toInt() ||
            height.toInt() != userData.height.toInt() ||
            gender != userData.gender

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        //Upper half of the screen holding the user stats
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.5f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                horizontalArrangement = Arrangement.Center
            ){
                Text( "U", color = PolarRed, style = MaterialTheme.typography.h1, fontSize = 12.sp )
                Text( "SERNAME", style = MaterialTheme.typography.h1, fontSize = 12.sp )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Text(text = userData.username)

            Spacer(modifier = Modifier.height(25.dp))

            //User stats
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){

                //Left column holding the stats description
                Column(
                    modifier = Modifier
                        .fillMaxWidth(.2f),
                    horizontalAlignment = Alignment.Start
                ){
                    Row {
                        Text("WEIGH", style = MaterialTheme.typography.h1, fontSize = 12.sp)
                        Text(
                            "T",
                            color = PolarRed,
                            style = MaterialTheme.typography.h1,
                            fontSize = 12.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        Text(
                            "H",
                            color = PolarRed,
                            style = MaterialTheme.typography.h1,
                            fontSize = 12.sp
                        )
                        Text("EIGHT", style = MaterialTheme.typography.h1, fontSize = 12.sp)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        Text("AG", style = MaterialTheme.typography.h1, fontSize = 12.sp)
                        Text(
                            "E",
                            color = PolarRed,
                            style = MaterialTheme.typography.h1,
                            fontSize = 12.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        Text(
                            "G",
                            color = PolarRed,
                            style = MaterialTheme.typography.h1,
                            fontSize = 12.sp
                        )
                        Text("ENDER", style = MaterialTheme.typography.h1, fontSize = 12.sp)
                    }
                }

                //Right column holding the actual user stats
                Column(
                    modifier = Modifier
                        .fillMaxWidth(.3f),
                    horizontalAlignment = Alignment.End
                ){
                        Text( text = "${userData.weight} kg")
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "${userData.height} cm")
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "${userData.age} y")
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = userData.gender)
                }
            }
            Spacer(modifier = Modifier.height(25.dp))

            //Toggle on and off the drop down menus
            OutlinedButton(
                onClick = { changeStatsVisibility = !changeStatsVisibility },
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
                Text(text = if ( changeStatsVisibility ) "Cancel" else "Change stats")
            }
            Spacer(modifier = Modifier.height(15.dp))

            //Button to save the stats if they have been changed
            if(saveButton && changeStatsVisibility){
            OutlinedButton(
                onClick = {
                    val newUser = UserDataClass(
                        userData.username,
                        userData.email,
                        weight.toInt(),
                        height.toInt(),
                        gender,
                        age.toInt(),
                        highscore.toInt()
                    )
                    userVM.changeStats( newUser )
                    userData = newUser
                    Log.d("userVM", userVM.userData.toString())
                },
                modifier = Modifier
                    .fillMaxWidth(.75f)
                    .height(40.dp),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colors.onPrimary,
                    backgroundColor = MaterialTheme.colors.surface
                ),
                elevation = ButtonDefaults.elevation(2.dp, 2.dp, 0.dp)
            )
            {
                Text(text = "Save stats")
            }
        }

        }

        //Changing stats drop down menus
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ){

            if (changeStatsVisibility) {

                //First stats row
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    //Age input field and dropdown menu
                    Column{
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
                            shape = MaterialTheme.shapes.large,
                            trailingIcon = {
                                Icon( painter = painterResource(R.drawable.dropdown_arrow), "description" )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                disabledBorderColor = PolarRed,
                                disabledLabelColor = PolarRed,
                                disabledTextColor = Color.White,
                                disabledTrailingIconColor = PolarRed
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
                    Column{
                        OutlinedTextField(
                            value = "$height cm",
                            label = { Text( text = "Height")},
                            onValueChange = { height = it },
                            singleLine = true,
                            enabled = false,
                            keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Number ),
                            modifier = Modifier
                                .fillMaxWidth(.47f)
                                .clickable { heightExpanded = !heightExpanded },
                            shape = MaterialTheme.shapes.large,
                            trailingIcon = {
                                Icon( painter = painterResource(R.drawable.dropdown_arrow), "description" )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                disabledBorderColor = PolarRed,
                                disabledLabelColor = PolarRed,
                                disabledTextColor = Color.White,
                                disabledTrailingIconColor = PolarRed
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
                }

                //Second stats row
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    //Weight input field and dropdown menu
                    Column{
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
                            shape = MaterialTheme.shapes.large,
                            trailingIcon = {
                                Icon( painter = painterResource(R.drawable.dropdown_arrow), "description" )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                disabledBorderColor = PolarRed,
                                disabledLabelColor = PolarRed,
                                disabledTextColor = Color.White,
                                disabledTrailingIconColor = PolarRed
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
                    Column{
                        OutlinedTextField(
                            value = gender,
                            label = { Text( text = "Gender")},
                            onValueChange = { gender = it },
                            singleLine = true,
                            enabled = false,
                            modifier = Modifier
                                .fillMaxWidth(.47f)
                                .clickable { genderExpanded = !genderExpanded },
                            shape = MaterialTheme.shapes.large,
                            trailingIcon = {
                                Icon( painter = painterResource(R.drawable.dropdown_arrow), "description" )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                disabledBorderColor = PolarRed,
                                disabledLabelColor = PolarRed,
                                disabledTextColor = Color.White,
                                disabledTrailingIconColor = PolarRed
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
                }
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }

}