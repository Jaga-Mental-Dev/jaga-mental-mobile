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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.Executor

class CaptureViewModel : ViewModel() {

    private val _fileUri = MutableStateFlow<String?>(null)
    val fileUri = _fileUri.asStateFlow()

    private val _fileBinary = MutableStateFlow<ByteArray?>(null)
    val fileBinary = _fileBinary.asStateFlow()

    fun capturePhoto(
        context: Context,
        cameraController: LifecycleCameraController,
        onPhotoCaptured: (String) -> Unit
    ) {
        val mainExecutor: Executor = ContextCompat.getMainExecutor(context)

        cameraController.takePicture(mainExecutor, object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                val correctedBitmap: Bitmap = image
                    .toBitmap()
                    .rotateBitmap(image.imageInfo.rotationDegrees)

                viewModelScope.launch {
                    val photoUri = saveBitmapToUri(context, correctedBitmap)
                    _fileUri.value = photoUri
                    _fileBinary.value = bitmapToByteArray(correctedBitmap)
                    onPhotoCaptured(photoUri)
                }
                image.close()
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("CaptureViewModel", "Error capturing image", exception)
            }
        })
    }

    private fun saveBitmapToUri(context: Context, bitmap: Bitmap): String {
        val file = File(context.cacheDir, context.getString(R.string.captured_photo_filename))
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        return file.toURI().toString()
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    // TODO: Implement api call to upload image
}