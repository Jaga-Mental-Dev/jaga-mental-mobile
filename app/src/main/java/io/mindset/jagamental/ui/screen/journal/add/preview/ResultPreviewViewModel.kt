package io.mindset.jagamental.ui.screen.journal.add.preview

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mindset.jagamental.data.domain.JournalRepository
import io.mindset.jagamental.data.model.response.JournalDataItem
import io.mindset.jagamental.utils.EmotionHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URI

class ResultPreviewViewModel(private val journalRepository: JournalRepository) : ViewModel() {

    // To generate random emotion
    val emotionHelper = EmotionHelper()

    private val _photoByte = MutableStateFlow<ByteArray?>(null)
    val photoByte = _photoByte.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _journalData = MutableStateFlow<JournalDataItem?>(null)
    val journalData = _journalData.asStateFlow()

    private val _suggestion = MutableStateFlow<String>("")
    val suggestion = _suggestion.asStateFlow()


    fun convertToByteArray(fileUri: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val file = File(URI(fileUri))
            val bitmap = BitmapFactory.decodeFile(file.path)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream)
            _photoByte.value = stream.toByteArray()
            withContext(Dispatchers.Main) {
                submitPhoto()
                _isLoading.value = false
            }
        }
    }

    private suspend fun submitPhoto() {
        // Implement API call here

        //Dummy Response
        val randomEmotion = emotionHelper.getRandomEmotion()

        val journalData = JournalDataItem(
            emotion = randomEmotion,
            selfie = "https://picsum.photos/800"
        )

        _suggestion.value = emotionHelper.getWordsByEmotion(journalData.emotion.toString())
        _journalData.value = journalData

        Log.d(
            "ResultPreviewViewModel",
            "Emotion: ${journalData.emotion}\nSuggestion: ${_suggestion.value}\nPhoto URL: ${journalData.selfie}"
        )
    }
}