package io.mindset.jagamental.ui.screen.journal

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.mindset.jagamental.navigation.Screen
import io.mindset.jagamental.ui.component.journal.AddJournalButton
import io.mindset.jagamental.ui.component.journal.CalendarHeader
import io.mindset.jagamental.ui.component.journal.EmptyJournal
import io.mindset.jagamental.ui.component.journal.JournalListItem
import io.mindset.jagamental.utils.StatusBarColorHelper
import io.mindset.jagamental.utils.isScrollingUp
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

    val systemUiColor = Screen.App.Journal.systemBarColor
    val randomJournalCount = remember { mutableIntStateOf(0) }
    val listState = rememberLazyListState()
    val journalsMock = List(randomJournalCount.intValue) { index ->
        JournalItem(
            title = "Journal ${index + 1}", content = "This is the content of journal ${index + 1}"
        )
    }
    val isJournalEmpty = remember { mutableStateOf(journalsMock.isEmpty()) }

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
                randomJournalCount.intValue = generateRandomJournalCount()
                isJournalEmpty.value = randomJournalCount.intValue == 0
            })

            if (isJournalEmpty.value) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    EmptyJournal(onClick = {navController.navigate(Screen.App.AddCapture)})
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize()
                ) {
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

        val showButton = journalsMock.isNotEmpty()

        if (showButton) {
            AddJournalButton(
                onclick = {
                    navController.navigate(Screen.App.AddCapture)
                },
                modifier = Modifier.align(Alignment.BottomEnd),
                isExpanded = !listState.isScrollingUp()
            )
        }
    }
    StatusBarColorHelper(systemUiColor, useDarkIcon = false)
}



@Preview
@Composable
fun PreviewJournalScreen() {
    JournalScreen(navController = rememberNavController(), paddingValues = PaddingValues(0.dp))
}