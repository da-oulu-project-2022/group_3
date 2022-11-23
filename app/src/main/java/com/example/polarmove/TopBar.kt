package com.example.polarmove

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.polarmove.ui.theme.PolarRed

@Composable
fun TopBar(){
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 20.dp)
    ) {
        Text(text = "C", style = MaterialTheme.typography.h1 )
        Text(text = "A", color = PolarRed, style = MaterialTheme.typography.h1 )
        Text(text = "T", style = MaterialTheme.typography.h1 )
        Text(text = " ADVENTURES", style = MaterialTheme.typography.h1 )
    }
}