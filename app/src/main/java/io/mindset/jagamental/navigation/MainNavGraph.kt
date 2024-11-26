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
import io.mindset.jagamental.ui.screen.profile.ProfileScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    screen: Screen
) {
    navigation<Screen.App>(
        startDestination = screen
    ) {
        composable<Screen.App.Dashboard> {
            DashboardScreen(navController, paddingValues)
        }

        composable<Screen.App.Journal> {
            JournalScreen(navController, paddingValues)
        }

        composable<Screen.App.Profile> {
            ProfileScreen(navController, paddingValues)
        }

        composable<Screen.App.CapturePhotoScreen> {
            CaptureScreen(navController)
        }

        composable<Screen.App.ResultPreviewScreen> { backStackEntry ->
            val photoUri = backStackEntry.arguments?.getString("photoUri")
            photoUri?.let {
                ResultPreviewScreen(it, navController)
            }
        }

        composable<Screen.App.PhotoResultScreen> { backStackEntry ->
            val photoUri = backStackEntry.arguments?.getString("photoUri")
            val emotion = backStackEntry.arguments?.getString("emotion")
            val words = backStackEntry.arguments?.getString("words")
            val photoUrl = backStackEntry.arguments?.getString("photoUrl")
            if (photoUri != null && emotion != null && words != null && photoUrl != null) {
                PhotoResultScreen(
                    photoUri,
                    emotion,
                    words,
                    photoUrl,
                    navController
                )
            }
        }

        composable<Screen.App.InputJournalScreen> {
            InputJournalScreen(navController, paddingValues)
        }
    }
}