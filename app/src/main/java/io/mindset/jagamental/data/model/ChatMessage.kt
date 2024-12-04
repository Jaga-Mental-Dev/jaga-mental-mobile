package io.mindset.jagamental.data.model

import java.time.LocalDateTime
import java.util.UUID

enum class Participant {
    Saya, JagaBot, ERROR, Journal
}

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    var text: String? = "",
    val participant: Participant = Participant.Saya,
    var isPending: Boolean = false,
    val displayed: Boolean = true,
    val datetime: LocalDateTime = LocalDateTime.now()
)