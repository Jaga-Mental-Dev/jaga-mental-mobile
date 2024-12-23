package io.mindset.jagamental.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import io.mindset.jagamental.ui.screen.root.RootScreen
import io.mindset.jagamental.ui.theme.JagaMentalTheme
import io.mindset.jagamental.utils.RemoteConfigHelper
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            JagaMentalTheme {
                RootScreen()
            }
        }

        lifecycleScope.launch {
            viewModel.checkLatestJournalStatus()
            val remoteConfigHelper = RemoteConfigHelper()
            val isUpdated = remoteConfigHelper.fetchAndActivate()
            if (isUpdated) {
                Log.i("RemoteConfig", "Remote Config Updated")
            } else {
                Log.i("RemoteConfig", "Remote Config Not Updated")
            }
            remoteConfigHelper.listenForUpdates()
        }
    }
}