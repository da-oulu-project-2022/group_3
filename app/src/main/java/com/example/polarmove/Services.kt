package com.example.polarmove

import android.util.Log
import com.google.firebase.auth.FirebaseAuth

fun login(email: String, password: String, userVM: UserVM, auth: FirebaseAuth){
    auth.signInWithEmailAndPassword( email, password)
        .addOnCompleteListener { task ->
            if( task.isSuccessful ){
                auth.currentUser?.let { user -> userVM.signIn( user )}
            } else {
                Log.d("error", "wrong credentials")
            }
        }
}