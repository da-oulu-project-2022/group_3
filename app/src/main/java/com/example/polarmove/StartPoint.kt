package com.example.polarmove

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@Composable
fun StartPoint(){

    val userVM: UserVM = viewModel()
    val auth = FirebaseAuth.getInstance()
    val user: FirebaseUser? = auth.currentUser

    if( user != null ){
        userVM.setUser( user )
        userVM.setEmail( user.email.toString() )
    }
    
    if( userVM.user.value == null ){
        LoginRegister( userVM = userVM, auth = auth)
    } else {
        LoggedInTestView( userVM = userVM, auth = auth )
    }
}