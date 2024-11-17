package io.mindset.jagamental.ui.screen.journal.add.preview

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URI

class ResultPreviewViewModel : ViewModel() {

    private val _photoByte = MutableStateFlow<ByteArray?>(null)
    val photoByte = _photoByte.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _emotionResult = MutableStateFlow<String?>(null)
    val emotionResult = _emotionResult.asStateFlow()

    private val _words = MutableStateFlow<String?>(null)
    val words = _words.asStateFlow()

    private val _photoUrl = MutableStateFlow<String?>(null)
    val photoUrl = _photoUrl.asStateFlow()

    fun convertToByteArray(fileUri: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val file = File(URI(fileUri))
            val bitmap = BitmapFactory.decodeFile(file.path)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream)
            _photoByte.value = stream.toByteArray()
            withContext(Dispatchers.Main) {
                _isLoading.value = false
                sendPhotoToServer()
            }
        }
    }

    private fun sendPhotoToServer() {
        // TODO: Implement the API call here
        // Simulate API response
        viewModelScope.launch {
            _emotionResult.value = "Happy"
            _words.value = "You look great!"
            _photoUrl.value = "https://doodleipsum.com/700?i=6bfd5163b573d0cf0c42d780cc8ecfd4"
        }
    }
}