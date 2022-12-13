package com.example.polarmove

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GameVM: ViewModel() {

    var ownGames = mutableStateOf( listOf<GameDataClass>())
    var highScoreGames = mutableStateOf( listOf<HighScoreDataClass>())

    var jumps =  mutableStateOf(0)
    var squats = mutableStateOf(0)
    var dashes = mutableStateOf(0)
    var score = mutableStateOf(0)
    var calories = mutableStateOf(2800)
    var hr = mutableStateOf(0)
    var batteryLevel = mutableStateOf(0)

    class Object(
        val x: Int = 0,
        val y: Int = 0,
        val z: Int = 0,
        val name: String = ""
    )

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
                            username = game.get("username") as String,
                            timestamp = game.get("timestamp") as Long
                        )
                        tempGamesList.add(fetchedGame)
                    }
                    ownGames.value = tempGamesList
                } else if ( value != null && value.isEmpty ){
                    ownGames.value = emptyList()
                }
            }
    }

    fun getHighScores(){
        Firebase.firestore
            .collection("games")
            .get()
            .addOnSuccessListener { value ->
                    val tempGamesList = mutableListOf<HighScoreDataClass>()
                    value.documents.forEach { game ->
                        val fetchedGame = HighScoreDataClass(
                            score = game.get("score") as Number,
                            player = game.get("username") as String
                        )
                        tempGamesList.add(fetchedGame)
                    }
                    val sliceNumber = if (tempGamesList.size >= 10) 9 else tempGamesList.size - 1
                    tempGamesList.sortByDescending { it.score.toInt() }
                    highScoreGames.value = tempGamesList.slice(0..sliceNumber)
            }
            .addOnFailureListener {
                highScoreGames.value = emptyList()
            }
    }

    fun setHr( hrValue: Int ) {
        hr.value = hrValue
    }
    fun setBatteryLevel( batteryReading: Int ){
        batteryLevel.value = batteryReading
    }

    fun calorieCalculator(time: Int, hr_avg: Int, weight: Int, age: Int, gender: String) {
        val timeMinutes = time.toDouble() / 60
        val var1 = if (gender == "Male") 0.6309 else 0.4472
        val var2 = if (gender == "Male") 0.1988 else 0.1263
        val var3 = if (gender == "Male") 0.2017 else 0.074
        val var4 = if (gender == "Male") 55.0969 else 20.4022

        calories.value = (timeMinutes * (var1 * hr_avg + var2 * weight + var3 * age - var4) / 4.184).toInt()
    }

    private fun pointsCalculator(time: Int ) {
        score.value = time + dashes.value * 10 + jumps.value * 10 + squats.value * 10
    }

    fun jumpIncrease( time: Int ){
        jumps.value += 1
        pointsCalculator( time )
    }
    fun squatIncrease( time: Int ){
        squats.value += 1
        pointsCalculator( time )
    }
    fun dashIncrease( time: Int ){
        dashes.value += 1
        pointsCalculator( time )
    }

    private fun gameReset(){
        jumps.value = 0
        squats.value = 0
        dashes.value = 0
        score.value = 0
    }

    fun saveGame(
        heart_rate_avg: Int,
        heart_rate_max: Int,
        user_id: String,
        username: String
    ){
        val timestamp = System.currentTimeMillis()
        val newGame = GameDataClass(
                                calories.value,
                                dashes.value,
                                heart_rate_avg,
                                heart_rate_max,
                                jumps.value,
                                squats.value,
                                score.value,
                                user_id,
                                username,
                                timestamp
                    )
        Firebase.firestore
            .collection("games")
            .document()
            .set(newGame)
            .addOnSuccessListener {
                gameReset()
            }
    }

}
