package io.mindset.jagamental.ui.screen.journal.add.input

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.mindset.jagamental.navigation.Screen
import io.mindset.jagamental.ui.component.journal.AnalyzeMoodLoading
import io.mindset.jagamental.ui.theme.primaryColor
import io.mindset.jagamental.utils.HelperComponent
import io.mindset.jagamental.utils.StatusBarColorHelper
import io.mindset.jagamental.utils.UiState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel


@SuppressLint("DefaultLocale")
@Composable
fun InputJournalScreen(
    journalId: String,
    navController: NavController,
    paddingValues: PaddingValues
) {
    val viewModel: InputJournalViewModel = koinViewModel()
    val journalData = viewModel.journalState.collectAsState()
    val helperComponent = get<HelperComponent>()
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val timerValue = viewModel.timerState.collectAsState()
    val scope = rememberCoroutineScope()

    val title = rememberSaveable { mutableStateOf("") }
    val description = rememberSaveable { mutableStateOf("") }

    val isLoading = remember { mutableStateOf(false) }
    val showSnackbar = remember { mutableStateOf(false) }
    val snackbarMessage = remember { mutableStateOf("") }

    StatusBarColorHelper(color = Color.White, useDarkIcon = true, navBarColor = Color.White)
    helperComponent.BackHandlerHelper(
        navController = navController,
        onBackAction = {
            viewModel.saveJournalStatus(journalId, false)
            viewModel.deleteJournal(journalId)
        },
    )

    LaunchedEffect(Unit) {
        viewModel.startTimer()
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopTimer()
        }
    }

    LaunchedEffect(showSnackbar.value) {
        if (showSnackbar.value)
            snackbarHostState.showSnackbar(
                snackbarMessage.value,
                duration = SnackbarDuration.Short,
                withDismissAction = true
            ).run {
                showSnackbar.value = !showSnackbar.value
            }
    }

    when (val state = journalData.value) {
        is UiState.Success -> {
            isLoading.value = false
            viewModel.saveJournalStatus(journalId, true)
            Log.i("InputJournalScreen", "Success: ${state.data}")
            LaunchedEffect(Unit) {
                navController.navigate(
                    Screen.App.JournalResultScreen(
                        journalId = journalId,
                    )
                ) {
                    launchSingleTop = true
                }
            }
        }

        is UiState.Error -> {
            Log.e("InputJournalScreen", "Error: ${state.message}")
            isLoading.value = false
            viewModel.resetState()
            showSnackbar.value = true
            snackbarMessage.value = "Oops! Terjadi kesalahan, silahkan coba lagi"
        }

        UiState.Loading -> {
            isLoading.value = true
            AnalyzeMoodLoading(
                text = "Menyimpan jurnal...",
            )
        }

        UiState.Idle -> {
            isLoading.value = false
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White),
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                topBar = {
                    TopBar(
                        onBackPressed = { navController.popBackStack() },
                    )
                },
                bottomBar = {
                    BottomAppBar(
                        modifier = Modifier
                            .imePadding()
                            .background(Color.White),
                        containerColor = Color.White,
                        actions = {},
                        floatingActionButton = {
                            FloatingActionButton(
                                onClick = {
                                    val (isValid, errorMessage) = validateInput(
                                        title = title.value,
                                        description = description.value
                                    )
                                    if (isValid) {
                                        isLoading.value = true
                                        scope.launch {
                                            viewModel.updateJournal(
                                                journalId = journalId,
                                                title = title.value,
                                                content = description.value,
                                            )
                                        }
                                    } else {
                                        isLoading.value = false
                                        showSnackbar.value = true
                                        snackbarMessage.value = errorMessage
                                    }
                                },
                                containerColor = primaryColor,
                                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                            ) {
                                Icon(Icons.AutoMirrored.Filled.Send, "Localized description")
                            }
                        }
                    )

                }
            ) { padding ->
                // i want to set bottom padding to 0
                val paddingValues = PaddingValues(
                    top = padding.calculateTopPadding(),
                    bottom = 0.dp
                )
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopEnd)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = String.format(
                                "%02d\n%02d",
                                timerValue.value / 60,
                                timerValue.value % 60
                            ),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 80.sp,
                                color = Color.LightGray,
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .background(Color.White.copy(0.8f)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(0.95f)
                                .border(0.dp, Color.Transparent, RectangleShape),
                            value = title.value,
                            textStyle = TextStyle(
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black,
                                fontSize = 18.sp
                            ),
                            onValueChange = { title.value = it },
                            enabled = !isLoading.value,
                            singleLine = false,
                            maxLines = 2,
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                            ),
                            shape = RectangleShape,
                            placeholder = {
                                Text(
                                    "Apa yang kamu rasakan?",
                                    color = Color.Gray,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp
                                )
                            }
                        )

                        CharacterCounter(
                            text = title.value,
                            minLength = 4
                        )

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(0.95f)
                                .border(0.dp, Color.Transparent, RectangleShape),
                            value = description.value,
                            onValueChange = { description.value = it },
                            enabled = !isLoading.value,
                            singleLine = false,
                            minLines = 2,
                            maxLines = 6,
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                            ),
                            shape = RectangleShape,
                            placeholder = {
                                Text(
                                    "Ceritakan pengalamanmu disini",
                                    color = Color.Gray,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            }
                        )
                        CharacterCounter(
                            text = description.value,
                            minLength = 20
                        )
                    }
                }
            }
        }
    }
}

fun validateInput(title: String, description: String): Pair<Boolean, String> {
    return when {
        title.isNotEmpty() && title.length < 4 -> Pair(
            false,
            "Judulnya terlalu pendek, coba tambahkan beberapa kata lagi."
        )

        title.isEmpty() -> Pair(false, "Tambahkan judul yuk biar lebih menarik.")
        description.isEmpty() -> Pair(false, "Jangan lupa tambahkan deskripsi ya.")
        description.isNotEmpty() && description.length < 20 -> Pair(
            false,
            "Deskripsi kamu terlalu pendek, coba tambahkan beberapa kata lagi."
        )

        else -> Pair(true, "")
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onBackPressed: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black
        ),
        title = {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Tulis Jurnal",
                fontWeight = FontWeight.SemiBold
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    onBackPressed()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        },
        actions = {
            Box(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
                    text = "3/4",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    )
}

@Composable
fun CharacterCounter(
    modifier: Modifier = Modifier,
    text: String,
    minLength: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = "${text.length}/$minLength",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}