package com.example.polarmove

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@Composable
fun StartPoint(){

    val userVM: UserVM = viewModel()
    val gameVM: GameVM = viewModel()

    val auth = FirebaseAuth.getInstance()
    val user: FirebaseUser? = auth.currentUser

    //Fetching user data and set user if Firebase user is logged in
    if( user != null ){
        userVM.setUser( user )
        userVM.setEmail( user.email.toString() )
        userVM.fetchUserData()
        gameVM.getOwnGames( user.email.toString() )
    }

    //Start screen based on authentication
    if( userVM.user.value == null ){
        LoginRegister( userVM, auth )
    } else {
        val navControl = rememberNavController()
        NavHost(navController = navControl, startDestination = "MainScreen"){
            composable( route = "MainScreen"){
                MainScreen( navControl, userVM, auth )
            }
            composable( route = "GameScreen"){
                GameScreen(navControl, userVM, gameVM )
            }
            composable( route = "UserInfo"){
                UserInfo( navControl, userVM )
            }
            composable( route = "HighScores"){
                HighScores( navControl, userVM, gameVM )
            }
            composable( route = "GameHistory"){
                GameHistory( navControl, userVM, gameVM )
            }
            composable( route = "AboutUs"){
                AboutUs( navControl, userVM )
            }
        }
    }

}