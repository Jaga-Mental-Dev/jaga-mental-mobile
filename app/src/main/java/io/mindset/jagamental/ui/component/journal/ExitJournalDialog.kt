package io.mindset.jagamental.ui.component.journal

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ExitJournalDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Peringatan") },
        text = { Text("Jurnal yang sedang dibuat tidak akan tersimpan. Apakah Anda yakin ingin keluar?") },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Ya, Keluar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}
