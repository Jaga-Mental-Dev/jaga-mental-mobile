package io.mindset.jagamental.utils

import android.graphics.Bitmap
import android.graphics.Matrix

fun Bitmap.rotateBitmap(rotationDegrees: Int, isFrontCamera: Boolean = true): Bitmap {
    val matrix = Matrix().apply {
        postRotate(rotationDegrees.toFloat())
        if (isFrontCamera) {
            postScale(-1f, 1f)
        }
    }

    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}
