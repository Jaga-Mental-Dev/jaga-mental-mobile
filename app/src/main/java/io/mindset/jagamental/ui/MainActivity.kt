package io.mindset.jagamental.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import io.mindset.jagamental.ui.screen.root.RootScreen
import io.mindset.jagamental.ui.theme.JagaMentalTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JagaMentalTheme {
                RootScreen()
            }
        }
    }
}