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
    var userName = mutableStateOf("")
    var highScore = mutableStateOf("")

    var userData = UserDataClass()

    fun setUser( userAuth: FirebaseUser? ) {
        user.value = userAuth
    }

    fun setEmail( email: String ){
        userEmail.value = email
    }

    fun setUsername( username: String){
        userName.value = username
    }

    fun setHighscore( highscore: String ){
        highScore.value = highscore
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
                    gender = fetchedData.get("gender") as String,
                    age = fetchedData.get("age") as Number,
                    highscore = fetchedData.get("highscore") as Number,
                    maxHr = fetchedData.get( "maxHr" ) as Number
                )
                setUsername(tempUserData.username)
                setHighscore(tempUserData.highscore.toString())
                setEmail(tempUserData.email)
                userData = tempUserData
            }
    }

    fun changeStats( newUser: UserDataClass ){
        Firebase.firestore.collection("users")
            .document(userEmail.value)
            .set(newUser)
            .addOnSuccessListener {
                userData = newUser
                fetchUserData()
            }
    }

}