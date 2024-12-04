package io.mindset.jagamental.ui.component.chatbot

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.mindset.jagamental.data.model.response.JournalData
import io.mindset.jagamental.ui.component.journal.JournalListItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalPickerSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onJournalSelected: (JournalData) -> Unit,
    journalList: List<JournalData>
) {
    val scope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }

    val filteredJournals = journalList.filter { journal ->
        journal.title?.contains(searchQuery, ignoreCase = true) == true ||
                journal.content?.contains(searchQuery, ignoreCase = true) == true
    }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        modifier = modifier,
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Cari Jurnal...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.LightGray.copy(0.5f),
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.LightGray.copy(0.5f)
                    )
                )
                Spacer(Modifier.size(8.dp))
                IconButton(
                    onClick = {
                        scope.launch {
                            onDismiss()
                        }
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.LightGray.copy(0.5f)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Close",
                        tint = Color.Red
                    )
                }
            }

            LazyColumn {
                itemsIndexed(filteredJournals) { _, journal ->
                    JournalListItem(
                        title = journal.title.toString(),
                        content = journal.content.toString(),
                        onItemClick = {
                            scope.launch {
                                onJournalSelected(journal)
                                sheetState.hide()
                            }
                        }
                    )
                }
            }
        }
    }
}
