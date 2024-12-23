package io.mindset.jagamental.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RootNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val startDestination = if (auth.currentUser != null) {
        Screen.App
    } else {
        Screen.Auth
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        authNavGraph(
            navController,
            screen = Screen.Auth.Login,
        )
        mainNavGraph(navController, paddingValues, screen = Screen.App.Dashboard)
    }
}