package io.mindset.jagamental.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import io.mindset.jagamental.ui.screen.login.LoginScreen
import io.mindset.jagamental.ui.screen.register.RegisterScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    screen: Screen
) {
    navigation<Screen.Auth>(
        startDestination = screen
    ) {
        composable<Screen.Auth.Login> {
            LoginScreen(navController)
        }

        composable<Screen.Auth.Register> {
            RegisterScreen(navController)
        }
    }
}