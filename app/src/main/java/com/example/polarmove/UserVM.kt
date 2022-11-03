package com.example.polarmove

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserVM: ViewModel() {
    var userEmail = mutableStateOf("")
    var userName = mutableStateOf("")
    var user = mutableStateOf<FirebaseUser?>(null)

    var userData = UserDataClass()

    fun setUser( userAuth: FirebaseUser? ) {
        user.value = userAuth
    }

    fun setEmail( email: String ){
        userEmail.value = email
    }

    fun setUsername( username: String ){
        userName.value = username
    }

    fun signIn( userInfo: FirebaseUser ){
        user.value = userInfo
        Log.d("message", "SignedIn")
    }

    fun signOut( auth: FirebaseAuth ){
        auth.signOut()
        user.value = null
    }

    fun fetchUserData() {
        Firebase.firestore
            .collection("users")
            .document(userEmail.value)
            .get()
            .addOnSuccessListener { fetchedData ->
                val tempUserData = UserDataClass(
                    username = fetchedData.get("username") as String,
                    email = userEmail.value,
                    weight = fetchedData.get("weight") as Number,
                    height = fetchedData.get("height") as Number,
                    age = fetchedData.get("age") as Number
                )
                userData = tempUserData
            }
    }
}