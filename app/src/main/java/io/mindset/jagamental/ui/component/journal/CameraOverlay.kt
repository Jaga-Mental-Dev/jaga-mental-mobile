package io.mindset.jagamental.ui.component.journal

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun OverlayView(
    modifier: Modifier,
    horizontalPadding: Dp = 100.dp,
    scannerHeight: Dp = 300.dp,
    scannerStrokeWidth: Dp = 2.dp,
    scannerColor: Color = Color.White,
    darkTint: Color = Color.Black.copy(alpha = 0.6f)
) {
    Canvas(modifier = modifier) {
        val horizontalPaddingPx = horizontalPadding.toPx()
        val scannerHeightPx = scannerHeight.toPx()
        val scannerStrokeWidthPx = scannerStrokeWidth.toPx()
        val scannerPath = getScannerPath(
            size = size,
            scannerHeight = scannerHeightPx,
            horizontalPadding = horizontalPaddingPx,
        )
        clipPath(path = scannerPath, clipOp = androidx.compose.ui.graphics.ClipOp.Difference) {
            drawRect(color = darkTint, size = size)
        }
        drawPath(
            path = scannerPath,
            color = scannerColor,
            style = Stroke(width = scannerStrokeWidthPx)
        )
    }
}


private fun getScannerPath(
    size: Size,
    scannerHeight: Float,
    horizontalPadding: Float
) = Path().apply {
    val center = Offset(size.width / 2, size.height / 2)
    addOval(
        oval = Rect(
            left = center.x - horizontalPadding * 1.5f,
            top = center.y - scannerHeight / 1.5f,
            right = center.x + horizontalPadding * 1.5f,
            bottom = center.y + scannerHeight / 1.5f
        )
    )
}

