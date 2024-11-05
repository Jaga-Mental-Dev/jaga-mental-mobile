package io.mindset.jagamental.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.mindset.jagamental.ui.screen.login.LoginScreen
import io.mindset.jagamental.ui.screen.onboarding.OnboardingScreen
import io.mindset.jagamental.ui.screen.welcome.WelcomeScreen
import kotlin.reflect.KClass

@Composable
fun MainNavHost(navController: NavHostController, startDestination: KClass<*> = Route.Welcome::class) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable<Route.Welcome> { WelcomeScreen(navController) }
        composable<Route.Onboarding> { OnboardingScreen(navController) }
        composable<Route.Login> { LoginScreen(navController) }
//        composable<Route.Auth> { AuthScreen(navController) }
//        composable<Route.Main> { MainScreen(navController) }
//        composable<Profile> { ProfileScreen(navController) }
    }
}