package com.example.cat_runner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cat_runner.ui.theme.Cat_runnerTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           LaneMovement()
            House()
                }

        }
    }


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Cat_runnerTheme {
        
    }
}


