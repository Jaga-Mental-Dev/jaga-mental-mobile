package io.mindset.jagamental.ui.components

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

@Composable
fun BackActionHandler(navController: NavHostController) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    androidx.activity.compose.BackHandler {
        if (navController.currentBackStackEntry == null) {
            showDialog = true
        } else {
            navController.popBackStack()
        }
    }

    if (showDialog) {
        ExitConfirmationDialog(
            onConfirm = {
                showDialog = false
                (context as ComponentActivity).finish()
            },
            onDismiss = {
                showDialog = false
            }
        )
    }
}