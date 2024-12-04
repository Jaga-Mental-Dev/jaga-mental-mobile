@file:Suppress("SameParameterValue")

package io.mindset.jagamental.ui.screen.journal.add.capture

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mindset.jagamental.R
import io.mindset.jagamental.utils.rotateBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.Executor

class CaptureViewModel : ViewModel() {

    private val _fileUri = MutableStateFlow<String?>(null)
    val fileUri = _fileUri.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun capturePhoto(
        context: Context,
        cameraController: LifecycleCameraController,
        onPhotoCaptured: (String) -> Unit
    ) {
        val mainExecutor: Executor = ContextCompat.getMainExecutor(context)

        _isLoading.value = true

        cameraController.takePicture(mainExecutor, object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                viewModelScope.launch(Dispatchers.IO) {
                    val correctedBitmap: Bitmap = image
                        .toBitmap()
                        .rotateBitmap(image.imageInfo.rotationDegrees)

                    val resizedBitmap = resizeBitmap(correctedBitmap, 800, 800)

                    val photoUri = saveBitmapToUri(context, resizedBitmap)
                    _fileUri.value = photoUri
                    withContext(Dispatchers.Main) {
                        onPhotoCaptured(photoUri)
                        _isLoading.value = false
                    }
                    image.close()
                }
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("CaptureViewModel", "Error capturing image", exception)
                _isLoading.value = false
            }
        })
    }

    private fun saveBitmapToUri(context: Context, bitmap: Bitmap): String {
        val file = File(context.cacheDir, context.getString(R.string.captured_photo_filename))
        val outputStream = FileOutputStream(file)
        resizeBitmap(bitmap, 800, 800)
            .compress(Bitmap.CompressFormat.JPEG, 60, outputStream)
        outputStream.flush()
        outputStream.close()
        return file.toURI().toString()
    }

    private fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val aspectRatio = width.toFloat() / height.toFloat()
        val newWidth: Int
        val newHeight: Int

        if (width > height) {
            newWidth = maxWidth
            newHeight = (maxWidth / aspectRatio).toInt()
        } else {
            newHeight = maxHeight
            newWidth = (maxHeight * aspectRatio).toInt()
        }

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }
}