package io.mindset.jagamental.utils

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import io.mindset.jagamental.navigation.Screen
import io.mindset.jagamental.ui.component.journal.ExitJournalDialog
import kotlinx.coroutines.launch

class HelperComponent() {
    @Composable
    fun BackHandlerHelper(
        navController: NavController,
        onBackAction: suspend () -> Unit
    ) {
        val scope = rememberCoroutineScope()
        var showExitDialog by remember { mutableStateOf(false) }

        BackHandler {
            showExitDialog = true
        }

        if (showExitDialog) {
            ExitJournalDialog(
                onDismiss = { showExitDialog = false },
                onConfirm = {
                    scope.launch {
                        onBackAction()
                        navController.navigate(Screen.App.MainJournalScreen) {
                            popUpTo(Screen.App.MainJournalScreen) { inclusive = true }
                        }
                    }
                }
            )
        }
    }
}