package io.mindset.jagamental.ui.screen.journal

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import io.mindset.jagamental.ui.components.CalendarHeader
import io.mindset.jagamental.ui.components.JournalListItem
import kotlin.random.Random

data class JournalItem(
    val title: String,
    val content: String,
)

@Composable
fun JournalScreen(navController: NavController, paddingValues: PaddingValues) {

    fun generateRandomJournalCount(): Int {
        return Random.nextInt(1, 10)
    }

    val randomJournalCount = remember { mutableStateOf(0) }

    val journalsMock = List(randomJournalCount.value) { index ->
        JournalItem(
            title = "Journal ${index + 1}",
            content = "This is the content of journal ${index + 1}"
        )
    }
        Column(
            modifier = Modifier.padding(paddingValues)
                .background(color = Color.White)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
        ) {
            CalendarHeader(
                onDateClick = { formattedDate ->
                    Log.d("JournalScreen", "Selected date: $formattedDate")
                    randomJournalCount.value = generateRandomJournalCount()
                }
            )

            LazyColumn {
                items(journalsMock) { journal ->
                    JournalListItem(
                        title = journal.title,
                        content = journal.content,
                        onItemClick = {
                            Toast.makeText(
                                navController.context,
                                "Journal ${journal.title} clicked!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }

        }
}