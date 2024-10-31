package io.mindset.jagamental.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import io.mindset.jagamental.navigation.MainNavHost
import io.mindset.jagamental.ui.theme.JagaMentalTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JagaMentalTheme {
                AppContent()
            }
        }
    }
}

@Composable
fun AppContent() {
    val navController = rememberNavController()
    MainNavHost(navController = navController)
}