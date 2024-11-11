package io.mindset.jagamental.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import io.mindset.jagamental.R

@Composable
fun ExitConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = context.getString(R.string.exit), fontWeight = FontWeight.Bold) },
        text = { Text(text = context.getString(R.string.exit_confirmation_text)) },
        containerColor = Color.White,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = context.getString(R.string.yes))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = context.getString(R.string.no))
            }
        }
    )
}