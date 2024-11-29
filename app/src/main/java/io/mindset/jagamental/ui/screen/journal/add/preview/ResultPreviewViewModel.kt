package io.mindset.jagamental.ui.screen.journal.add.preview

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mindset.jagamental.data.domain.JournalRepository
import io.mindset.jagamental.data.model.response.JournalData
import io.mindset.jagamental.utils.EmotionHelper
import io.mindset.jagamental.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URI

class ResultPreviewViewModel(private val journalRepository: JournalRepository) : ViewModel() {

    private val _photoByte = MutableStateFlow<ByteArray?>(null)
    val photoByte = _photoByte.asStateFlow()

    private val _journalState = MutableStateFlow<UiState<JournalData>>(UiState.Idle)
    val journalState = _journalState.asStateFlow()

    private val _suggestion = MutableStateFlow<String>("")
    val suggestion = _suggestion.asStateFlow()

    private val emotionHelper = EmotionHelper()

    fun convertToByteArray(fileUri: String) {
        _journalState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val file = File(URI(fileUri))
            val bitmap = BitmapFactory.decodeFile(file.path)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream)
            _photoByte.value = stream.toByteArray()
            withContext(Dispatchers.Main) {
                submitPhoto(file)
            }
        }
    }

    private fun submitPhoto(file: File) {
        viewModelScope.launch {
            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
            submitApi(body)
        }
    }

    suspend fun submitApi(body: MultipartBody.Part) {
        journalRepository.postCreateJournal(body).collect { response ->
            Log.d("ResultPreviewViewModel", "submitPhoto: $response")
            when (response) {
                is UiState.Success -> {
                    Log.d("ResultPreviewViewModel", "submitPhoto: ${response.data}")
                    _journalState.value = UiState.Success<JournalData>(response.data)
                    val emotion: String = response.data.initialEmotion ?: ""
                    _suggestion.value = emotionHelper.getWordsByEmotion(emotion)
                }

                is UiState.Error -> {
                    _journalState.value = UiState.Error(response.message)
                }

                else -> {
                    Log.d("ResultPreviewViewModel", "submitPhoto: else")
                    _journalState.value = UiState.Loading
                }
            }
        }
    }
}