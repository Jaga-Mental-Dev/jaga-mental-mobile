package io.mindset.jagamental.ui.screen.journal

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import io.mindset.jagamental.data.model.response.JournalData
import io.mindset.jagamental.navigation.Screen
import io.mindset.jagamental.ui.component.journal.AddJournalButton
import io.mindset.jagamental.ui.component.journal.CalendarHeader
import io.mindset.jagamental.ui.component.journal.EmptyJournal
import io.mindset.jagamental.ui.component.journal.JournalListItem
import io.mindset.jagamental.utils.StatusBarColorHelper
import io.mindset.jagamental.utils.UiState
import io.mindset.jagamental.utils.isScrollingUp
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JournalScreen(navController: NavController, paddingValues: PaddingValues) {
    val viewModel: JournalViewModel = koinViewModel()
    val journalState by viewModel.journalState.collectAsState()

    val listState = rememberLazyListState()
    val selectedDate = remember { mutableStateOf("") }
    val isJournalEmpty = remember { mutableStateOf(false) }

    val systemUiColor = Color(0xFF194A47)

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
            CalendarHeader(onDateClick = { date ->
                selectedDate.value = date
                Log.i("JournalScreen", "Selected date: $date")
                viewModel.resetState()
                viewModel.getJournalsByDate(date)
            })

            when (val state = journalState) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is UiState.Success -> {
                    val journals = state.data ?: emptyList()

                    if (journals.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            isJournalEmpty.value = true
                            EmptyJournal(onClick = { navController.navigate(Screen.App.CapturePhotoScreen) })
                        }
                    } else {
                        isJournalEmpty.value = false
                        LazyColumn(
                            state = listState,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(journals, key = { it?.id!! }) { journal ->
                                var visible = remember { mutableStateOf(false) }

                                LaunchedEffect(journal?.id) {
                                    delay(100 * journals.indexOf(journal).toLong())
                                    visible.value = true
                                }

                                AnimatedVisibility(
                                    visible = visible.value,
                                    enter = fadeIn() + expandHorizontally(),
                                    exit = fadeOut() + slideOutHorizontally(),
                                ) {
                                    JournalListItem(
                                        journal = journal ?: JournalData(),
                                        onItemClick = {
                                            navController.navigate(
                                                Screen.App.JournalResultScreen(journal?.id.toString())
                                            ) {
                                                launchSingleTop = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                is UiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Error: ${state.message}", color = Color.Red)
                    }
                }

                UiState.Idle -> {
                    // Initial state, do nothing
                }
            }
        }

        val showButton = !isJournalEmpty.value
        if (showButton) {
            AddJournalButton(
                onclick = {
                    navController.navigate(Screen.App.CapturePhotoScreen)
                },
                modifier = Modifier.align(Alignment.BottomEnd),
                isExpanded = !listState.isScrollingUp()
            )
        }
    }
    StatusBarColorHelper(systemUiColor, useDarkIcon = false)
}