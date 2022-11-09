package com.example.polarmove

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GameVM: ViewModel() {

    var ownGames = mutableStateOf( listOf<GameDataClass>())
    var highScoreGames = mutableStateOf( listOf<GameDataClass>())

    fun getOwnGames( email: String ){
        Firebase.firestore
            .collection("games")
            .whereEqualTo("user_id", email)
            .addSnapshotListener { value, error ->
                if (error != null ) {
                    error.message?.let { Log.d("err message: ", it ) }
                } else if ( value != null && !value.isEmpty ){
                    val tempGamesList = mutableListOf<GameDataClass>()
                    value.documents.forEach { game ->
                        val fetchedGame = GameDataClass(
                            calories = game.get("calories") as Number,
                            dashes = game.get("dashes") as Number,
                            heart_rate_avg = game.get("heart_rate_avg") as Number,
                            heart_rate_max = game.get("heart_rate_max") as Number,
                            jumps = game.get("jumps") as Number,
                            squats = game.get("squats") as Number,
                            score = game.get("score") as Number,
                            user_id = game.get("user_id") as String,
                            username = game.get("username") as String
                        )
                        tempGamesList.add(fetchedGame)
                    }
                    ownGames.value = tempGamesList
                    Log.d("Games", ownGames.toString())
                } else if ( value != null && value.isEmpty ){
                    ownGames.value = emptyList()
                }
            }
    }

}