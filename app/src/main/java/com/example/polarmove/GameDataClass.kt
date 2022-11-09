package com.example.polarmove

data class GameDataClass(
    var calories: Number = 0,
    var dashes: Number = 0,
    val heart_rate_avg: Number = 0,
    val heart_rate_max: Number = 0,
    var jumps: Number = 0,
    var squats: Number = 0,
    var score: Number = 0,
    var user_id: String = "",
    var username: String = ""
)