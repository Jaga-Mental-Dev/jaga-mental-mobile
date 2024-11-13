package io.mindset.jagamental.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import io.mindset.jagamental.ui.screen.dashboard.DashboardScreen
import io.mindset.jagamental.ui.screen.journal.JournalScreen
import io.mindset.jagamental.ui.screen.profile.ProfileScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
) {
    navigation<Screen.App>(
        startDestination = Screen.App.Dashboard::class
    ) {
        composable<Screen.App.Dashboard> {
            DashboardScreen(navController)
        }

        composable<Screen.App.Journal> {
            JournalScreen(navController)
        }

        composable<Screen.App.Profile> {
            ProfileScreen(navController)
        }
    }
}