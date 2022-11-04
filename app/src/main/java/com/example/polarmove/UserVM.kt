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
    var user = mutableStateOf<FirebaseUser?>(null)

    var userData = UserDataClass()

    fun setUser( userAuth: FirebaseUser? ) {
        user.value = userAuth
    }

    fun setEmail( email: String ){
        userEmail.value = email
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
                    gender = fetchedData.get("gender") as String,
                    age = fetchedData.get("age") as Number
                )
                userData = tempUserData
            }
    }

    fun changeStats( weight: Number, height: Number, gender: String, age: Number ){
        val newUser = UserDataClass(userData.username, userData.email, weight, height, gender, age)
        Firebase.firestore.collection("users")
            .document(userEmail.value)
            .set(newUser)
            .addOnSuccessListener {
                userData = newUser
                fetchUserData()
            }
    }

}