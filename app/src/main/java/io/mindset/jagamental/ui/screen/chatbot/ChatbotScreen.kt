package io.mindset.jagamental.ui.screen.chatbot

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.mindset.jagamental.data.model.ChatMessage
import io.mindset.jagamental.data.model.Participant
import io.mindset.jagamental.ui.component.chatbot.JournalPickerSheet
import io.mindset.jagamental.ui.component.chatbot.MessageInputCard
import io.mindset.jagamental.utils.StatusBarColorHelper
import io.mindset.jagamental.utils.UiState
import io.mindset.jagamental.utils.formatRelative
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatbotScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val chatViewModel: ChatViewModel = koinViewModel()
    val chatUiState by chatViewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val isBotTyping = chatViewModel.isBotTyping.collectAsState()
    val scope = rememberCoroutineScope()
    val journals by chatViewModel.journals.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var showPicker by remember { mutableStateOf(false) }

    StatusBarColorHelper(useDarkIcon = true)
    Scaffold(
        containerColor = Color.White,
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "JAGABot",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            if (isBotTyping.value) {
                                Text(
                                    text = "Sedang mengetik...",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    actions = {},
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White
                    )
                )

                if (isBotTyping.value) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        bottomBar = {
            MessageInputCard(
                modifier = Modifier,
                onSendMessage = { inputText ->
                    chatViewModel.sendMessage(inputText)
                },
                resetScroll = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                },
                isBotTyping = isBotTyping.value,
                onPickJournal = {
                    scope.launch {
                        chatViewModel.getAllJournal()
                        showPicker = true
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            ChatList(
                modifier = modifier
                    .background(Color.White),
                chatUiState.messages,
                listState
            )

            when (val state = journals) {
                is UiState.Loading -> {}
                is UiState.Error -> {
                    LaunchedEffect(Unit) {
                        sheetState.hide()
                    }
                }

                is UiState.Success -> {
                    if (showPicker) {
                        JournalPickerSheet(
                            modifier = Modifier,
                            sheetState = sheetState,
                            onDismiss = {
                                showPicker = false
                            },
                            onJournalSelected = {
                                scope.launch {
                                    chatViewModel.sendJournalAsMessage(it)
                                    showPicker = false
                                }
                            },
                            journalList = state.data.filterNotNull()
                        )
                    }
                }

                is UiState.Idle -> {}
            }

        }
    }

    LaunchedEffect(chatUiState.messages.size) {
        if (chatUiState.messages.isNotEmpty()) {
            listState.scrollToItem(chatUiState.messages.size - 1)
        }
    }
}


@Composable
fun ChatList(
    modifier: Modifier = Modifier
        .imePadding(),
    chatMessages: List<ChatMessage>,
    listState: LazyListState
) {
    LazyColumn(
        modifier = modifier,
        state = listState,
        reverseLayout = false
    ) {
        itemsIndexed(chatMessages) { _, chatMessage ->
            if (chatMessage.displayed) {
                ChatBubbleItem(
                    chatMessage = chatMessage
                )
            }
        }

    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ChatBubbleItem(
    modifier: Modifier = Modifier,
    chatMessage: ChatMessage
) {
    val isModelMessage = chatMessage.participant == Participant.JagaBot ||
            chatMessage.participant == Participant.ERROR

    val isJournalMessage = chatMessage.participant == Participant.Journal

    val backgroundColor = when (chatMessage.participant) {
        Participant.JagaBot -> MaterialTheme.colorScheme.primaryContainer
        Participant.Saya -> MaterialTheme.colorScheme.tertiaryContainer
        Participant.ERROR -> MaterialTheme.colorScheme.errorContainer
        Participant.Journal -> MaterialTheme.colorScheme.tertiaryContainer
    }

    val bubbleShape = if (isModelMessage) {
        RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)
    } else {
        RoundedCornerShape(20.dp, 4.dp, 20.dp, 20.dp)
    }

    val horizontalAlignment = if (isModelMessage) {
        Alignment.Start
    } else {
        Alignment.End
    }

    Column(
        horizontalAlignment = horizontalAlignment,
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = chatMessage.participant.name,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        BoxWithConstraints {
            Card(
                colors = CardDefaults.cardColors(containerColor = backgroundColor),
                shape = bubbleShape,
                modifier = Modifier.widthIn(max = maxWidth * 0.9f)
            ) {
                chatMessage.text?.let {
                    Text(
                        text = if (isJournalMessage) "📝 Jurnal Record" else it,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
        Text(
            text = chatMessage.datetime.formatRelative(),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 4.dp),
            color = Color.Gray
        )
    }
}