package io.mindset.jagamental.ui.screen.journal

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.mindset.jagamental.R
import io.mindset.jagamental.ui.components.CalendarHeader
import io.mindset.jagamental.ui.components.JournalListItem
import io.mindset.jagamental.ui.components.emptyScreen.EmptyScreen
import io.mindset.jagamental.ui.components.newJournal.JournalButton
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
            title = "Journal ${index + 1}", content = "This is the content of journal ${index + 1}"
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
        ) {
            CalendarHeader(onDateClick = { formattedDate ->
                Log.d("JournalScreen", "Selected date: $formattedDate")
                randomJournalCount.value = generateRandomJournalCount()
            })

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                if (journalsMock.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(), // Makes Box take up all available space in LazyColumn
                            contentAlignment = Alignment.Center // Centers its content
                        ) {
                            EmptyScreen(onClick = {})
                        }
                    }
                } else {
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

        if (journalsMock.isNotEmpty()) {
            JournalButton(
                onclick = {},
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}

@Preview
@Composable
fun PreviewJournalScreen() {
    JournalScreen(navController = rememberNavController(), paddingValues = PaddingValues(0.dp))
}