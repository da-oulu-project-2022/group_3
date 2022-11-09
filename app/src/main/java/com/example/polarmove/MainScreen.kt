package com.example.polarmove

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.polarmove.ui.theme.PolarRed
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainScreen(
    navControl: NavController,
    userVM: UserVM,
    auth: FirebaseAuth,
    scState: ScaffoldState
) {
    Scaffold(
        scaffoldState = scState,
        topBar = { TopBar() },
        content = { MainScreenContent( navControl, userVM , auth )}
    )
}

@Composable
fun MainScreenContent( navControl: NavController, userVM: UserVM, auth: FirebaseAuth){

    val username by remember { mutableStateOf(userVM.userName)}
    val highscore by remember { mutableStateOf(userVM.highScore)}
    
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Column(
            modifier = Modifier
                .fillMaxHeight(.2f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center
            ){
                Text( "P", color = PolarRed, style = MaterialTheme.typography.h1, fontSize = 15.sp )
                Text( "lAYER", style = MaterialTheme.typography.h1, fontSize = 15.sp )
            }
            Spacer(modifier = Modifier.height(5.dp))

            Text(text = username.value, fontSize = 20.sp )
            Spacer(modifier = Modifier.height(15.dp))

            Row(
                horizontalArrangement = Arrangement.Center
            ){
                Text( "HIGHSCOR", style = MaterialTheme.typography.h1, fontSize = 15.sp )
                Text( "E", color = PolarRed, style = MaterialTheme.typography.h1, fontSize = 15.sp )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = highscore.value, fontSize = 25.sp)
        }
        
        Column(
            modifier = Modifier.fillMaxHeight(.6f),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                onClick = { navControl.navigate("GameScreen") },
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
                Text(text = "New game")
            }
            OutlinedButton(
                onClick = { navControl.navigate("UserInfo") },
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
                Text(text = "User info")
            }
            OutlinedButton(
                onClick = { navControl.navigate("HighScores") },
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
                Text(text = "High scores")
            }
            OutlinedButton(
                onClick = { navControl.navigate("GameHistory") },
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
                Text(text = "Game history")
            }
            OutlinedButton(
                onClick = { navControl.navigate("AboutUs") },
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
                Text(text = "About us")
            }
            OutlinedButton(
                onClick = {userVM.signOut( auth )},
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
                Text(text = "Sign out")
            }
        }
        
    }

}