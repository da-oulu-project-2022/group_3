package com.example.polarmove

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun login(email: String, password: String, userVM: UserVM, auth: FirebaseAuth){
    auth.signInWithEmailAndPassword( email, password)
        .addOnCompleteListener { task ->
            if( task.isSuccessful ){
                userVM.setEmail(email)
//                userVM.fetchUserData()
                auth.currentUser?.let { user -> userVM.signIn( user )}
            } else {
                Log.d("error", "wrong credentials")
            }
        }
}

fun register(email: String, password: String, username: String, userVM: UserVM, auth: FirebaseAuth){
    auth.createUserWithEmailAndPassword( email, password )
        .addOnSuccessListener {
            val user = UserDataClass(username = username, email = email)
            Firebase.firestore.collection("users")
                .document(email)
                .set(user)
                .addOnSuccessListener {
                    userVM.setEmail(email)
                    auth.currentUser?.let { user -> userVM.signIn( user )}
                }
        }
}