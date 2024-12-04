package io.mindset.jagamental.navigation

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import io.mindset.jagamental.ui.screen.chatbot.ChatbotScreen
import io.mindset.jagamental.ui.screen.dashboard.DashboardScreen
import io.mindset.jagamental.ui.screen.journal.JournalScreen
import io.mindset.jagamental.ui.screen.journal.add.capture.CaptureScreen
import io.mindset.jagamental.ui.screen.journal.add.input.InputJournalScreen
import io.mindset.jagamental.ui.screen.journal.add.photoresult.PhotoResultScreen
import io.mindset.jagamental.ui.screen.journal.add.preview.ResultPreviewScreen
import io.mindset.jagamental.ui.screen.journal.result.JournalResultScreen
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

        composable<Screen.App.MainJournalScreen> {
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
            val journalId = backStackEntry.arguments?.getString("journalId") ?: ""
            val emotion = backStackEntry.arguments?.getString("emotion")
            val words = backStackEntry.arguments?.getString("words")
            val photoUrl = backStackEntry.arguments?.getString("photoUrl")
            Log.d("PhotoResultScreen", "PhotoResultScreen: $photoUrl, $emotion, $words")
            if (journalId.isNotEmpty() && !emotion.isNullOrEmpty() && !words.isNullOrEmpty() && !photoUrl.isNullOrEmpty()) {
                PhotoResultScreen(
                    journalId,
                    emotion,
                    words,
                    photoUrl,
                    navController
                )
            } else {
                Log.e("PhotoResultScreen", "Missing arguments")
                navController.popBackStack()
            }
        }

        composable<Screen.App.InputJournalScreen> {
            val journalId: String = it.arguments?.getString("journalId") ?: ""
            if (journalId.isNotEmpty()) {
                InputJournalScreen(journalId, navController, paddingValues)
            } else {
                Log.e("InputJournalScreen", "Missing arguments")
                navController.popBackStack()
            }
        }

        composable<Screen.App.JournalResultScreen> {
            val journalId = it.arguments?.getString("journalId") ?: ""
            if (journalId.isEmpty()) {
                Log.e("JournalResultScreen", "Missing arguments")
            } else {
                JournalResultScreen(journalId, navController)
            }
        }

        composable<Screen.App.ChatBotScreen> {
            ChatbotScreen(
                navController = navController,
            )
        }
    }
}