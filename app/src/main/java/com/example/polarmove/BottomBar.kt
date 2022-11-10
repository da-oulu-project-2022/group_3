package com.example.polarmove

import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomBar( navControl: NavController ){
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 20.dp)
    ) {
        OutlinedButton(
            onClick = {navControl.navigate("MainScreen")},
            modifier = Modifier
                .fillMaxWidth(.75f)
                .height(40.dp),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colors.onPrimary,
            ),
            elevation = ButtonDefaults.elevation(2.dp, 2.dp, 0.dp)
        ) {
            Text( text = "Main screen")
        }
    }
}