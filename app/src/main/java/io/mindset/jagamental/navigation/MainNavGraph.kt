package io.mindset.jagamental.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import io.mindset.jagamental.ui.screen.dashboard.DashboardScreen
import io.mindset.jagamental.ui.screen.journal.JournalScreen
import io.mindset.jagamental.ui.screen.journal.add.capture.CaptureScreen
import io.mindset.jagamental.ui.screen.journal.add.input.InputJournalScreen
import io.mindset.jagamental.ui.screen.journal.add.photoresult.PhotoResultScreen
import io.mindset.jagamental.ui.screen.journal.add.preview.ResultPreviewScreen
import io.mindset.jagamental.ui.screen.login.LoginScreen
import io.mindset.jagamental.ui.screen.profile.ProfileScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    screen: Screen
) {
    navigation<Screen.App>(
        startDestination = screen
    ) {

        composable<Screen.Auth> {
            LoginScreen(navController)
        }

        composable<Screen.App.Dashboard> {
            DashboardScreen(navController, paddingValues)
        }

        composable<Screen.App.Journal> {
            JournalScreen(navController, paddingValues)
        }

        composable<Screen.App.Profile> {
            ProfileScreen(navController, paddingValues)
        }

        composable<Screen.App.AddCapture> {
            CaptureScreen(navController)
        }

        composable<Screen.App.ResultPreviewScreen> { backStackEntry ->
            val photoUri = backStackEntry.arguments?.getString("photoUri")
            photoUri?.let {
                ResultPreviewScreen( it, navController)
            }
        }

        composable<Screen.App.PhotoResultScreen> { backStackEntry ->
            val photoUri = backStackEntry.arguments?.getString("photoUri")
            val emotion = backStackEntry.arguments?.getString("emotion")
            photoUri?.let { uri ->
                emotion?.let { emo ->
                    PhotoResultScreen(uri, emo, navController)
                }
            }
        }

        composable<Screen.App.InputJournalScreen> {
            InputJournalScreen(navController, paddingValues)
        }
    }
}