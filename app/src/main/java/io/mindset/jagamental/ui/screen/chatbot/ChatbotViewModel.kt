package io.mindset.jagamental.ui.screen.chatbot

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.asTextOrNull
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import com.google.firebase.auth.FirebaseAuth
import io.mindset.jagamental.data.domain.JournalRepository
import io.mindset.jagamental.data.model.ChatMessage
import io.mindset.jagamental.data.model.Participant
import io.mindset.jagamental.data.model.response.JournalData
import io.mindset.jagamental.utils.ChatUiState
import io.mindset.jagamental.utils.RemoteConfigHelper
import io.mindset.jagamental.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val journalRepository: JournalRepository
) : ViewModel() {

    val remoteConfigHelper = RemoteConfigHelper()
    val apiKey = remoteConfigHelper.getString("gemini_api_key")

    private val _isBotTyping = MutableStateFlow(true)
    val isBotTyping = _isBotTyping.asStateFlow()

    private val _journals = MutableStateFlow<UiState<List<JournalData?>>>(UiState.Loading)
    val journals = _journals.asStateFlow()

    private val systemInstruction = MutableStateFlow(
        """
        Anda adalah seorang asisten empatis dan profesional dalam bidang kesehatan mental.
        Anda adalah asisten pribadi di aplikasi Jaga Mental.
        Tujuan Anda adalah:
        - Memberikan dukungan awal dan mendengarkan dengan penuh perhatian
        - Tidak memberikan diagnosis medis
        - Menjaga kerahasiaan dan menciptakan ruang aman untuk berbicara
        Prinsip utama:
        - Selalu bersikap non-judgmental
        - Fokus pada perasaan dan pengalaman pengguna
        - Prioritaskan untuk konsultasi kepada profesional
        - Jangan terlalu banyak obrolan, segera arahkan ke profesional jika sudah melebihi 5 obrolan
        - Arahkan untuk ke halaman dashboard aplikasi untuk daftar professional yang tersedia
        - Jangan bertanya jika tidak perlu
        - Usahakan balasan tidak melebihi 400 karakter
        - Gunakan bahasa yang lebih natural
        - Jika ingin menyebutkan judul, abaikan simbol "~"
        - Gunakan markdown agar lebih menarik
        - Anda boleh menggunakan bahasa yang sedikit kasual, dan emoji agar lebih ekspresif
        """.trimMargin()
    )

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash-latest",
        apiKey = apiKey,
        generationConfig = generationConfig {
            temperature = 0.7f
            topK = 40
            topP = 0.5f
            maxOutputTokens = 2048
            responseMimeType = "text/plain"
        },
        systemInstruction = content { text(systemInstruction.value) }
    )

    private val chat = generativeModel.startChat(history = listOf())

    private val _uiState = MutableStateFlow(ChatUiState(chat.history.filter {
        it.role == "user" || it.role == "model"
    }.map { content ->
        ChatMessage(
            text = content.parts.first().asTextOrNull() ?: "",
            participant = if (content.role == "user") Participant.Saya else Participant.JagaBot,
            isPending = false
        )
    }))

    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        _isBotTyping.value = true
        initMessage()
        updateRemoteConfig()
        Log.i("ChatViewModel", "apiKey: $apiKey")
    }

    fun updateRemoteConfig() {
        viewModelScope.launch {
            val updated = remoteConfigHelper.fetchAndActivate()
            if (updated) {
                Log.i("ChatViewModel", "updateRemoteConfig: Updated")
            } else {
                Log.i("ChatViewModel", "updateRemoteConfig: Not Updated")
            }
        }
    }

    fun getAllJournal() {
        viewModelScope.launch {
            try {
                journalRepository.getJournals().collect {
                    _journals.value = it
                }
            } catch (e: Exception) {
                Log.i("ChatViewModel", "getAllJournal: ${e.message}")
                _journals.value = UiState.Error(e.message.toString())
            }
        }
    }

    private fun initMessage() {
        _isBotTyping.value = true
        viewModelScope.launch {
            val currentUser = FirebaseAuth.getInstance().currentUser
            val userData = "Nama saya ${currentUser?.displayName.orEmpty()}"
            sendMessage(userData, false)
        }
    }

    fun sendJournalAsMessage(journal: JournalData) {
        _isBotTyping.value = true
        val selectedJournalContext = """
            Saya memilih salah satu jurnal, tolong bantu saya dengan ini:
            judul: ~${journal.title}~
            Isi: ${journal.content}
            Hasil Analisa Emosi dari aplikasi (mungkin tidak akurat): ${journal.emotion}
        """.trimIndent()

        sendChatMessage(selectedJournalContext, Participant.Journal)
    }

    fun sendMessage(userMessage: String, isDisplayed: Boolean = true) {
        _isBotTyping.value = true
        sendChatMessage(userMessage, Participant.Saya, isDisplayed)
    }

    private fun sendChatMessage(
        message: String,
        participant: Participant,
        isDisplayed: Boolean = true
    ) {
        _uiState.value.addMessage(
            ChatMessage(
                text = message,
                participant = participant,
                isPending = true,
                displayed = isDisplayed
            )
        )

        viewModelScope.launch {
            try {
                val response = chat.sendMessage(message)

                _uiState.value.replaceLastPendingMessage()
                response.text.let { modelResponse ->
                    _uiState.value.addMessage(
                        ChatMessage(
                            text = modelResponse.toString(),
                            participant = Participant.JagaBot,
                            isPending = false
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.value.replaceLastPendingMessage()
                _uiState.value.addMessage(
                    ChatMessage(text = e.localizedMessage, participant = Participant.ERROR)
                )
            }
            _isBotTyping.value = false
        }
    }
}
