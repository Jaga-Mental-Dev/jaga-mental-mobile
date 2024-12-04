package io.mindset.jagamental.ui.component.chatbot

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.rounded.PostAdd
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp


@Composable
fun MessageInputCard(
    modifier: Modifier,
    onSendMessage: (String) -> Unit,
    resetScroll: () -> Unit = {},
    isBotTyping: Boolean = false,
    onPickJournal: () -> Unit = {}
) {
    var userMessage by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .systemBarsPadding()
            .imePadding(),
        colors = CardDefaults.cardColors(
            containerColor = if (isBotTyping) Color.Gray else Color.LightGray,
        ),
        shape = RoundedCornerShape(100f)
    ) {
        Row(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = {
                    onPickJournal()
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.PostAdd,
                    contentDescription = "",
                    modifier = modifier,
                    tint = Color.DarkGray
                )
            }
            OutlinedTextField(
                enabled = !isBotTyping,
                placeholder = { Text("Apa yang kamu rasakan?") },
                value = userMessage,
                onValueChange = { userMessage = it },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                ),
                modifier = modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
                    .weight(0.85f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(0.5f)
            )
            IconButton(
                enabled = !isBotTyping,
                onClick = {
                    if (userMessage.isNotBlank()) {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        onSendMessage(userMessage)
                        userMessage = ""
                        resetScroll()
                    }
                },
                modifier = modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
                    .weight(0.15f)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = "",
                    modifier = modifier
                )
            }
        }
    }
}
