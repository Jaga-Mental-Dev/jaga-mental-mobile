package io.mindset.jagamental.ui.component.journal

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.tooling.preview.Preview
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
            left = center.x - horizontalPadding * 1.2f,
            top = center.y - scannerHeight / 2,
            right = center.x + horizontalPadding * 1.2f,
            bottom = center.y + scannerHeight / 2
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewOverlayView() {
    Box(modifier = Modifier.fillMaxSize()
        .background(Color.DarkGray)) {
        OverlayView(modifier = Modifier)
    }
}