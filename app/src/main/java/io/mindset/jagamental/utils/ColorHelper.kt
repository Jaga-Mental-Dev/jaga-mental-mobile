package io.mindset.jagamental.utils

import androidx.compose.ui.graphics.Color

object ColorHelper {
    fun getColorByLabel(label: String): Color {
        return when (label) {
            "Senang" -> Color(0xFF00E676)
            "Sedih" -> Color(0xFF2196F3)
            "Netral" -> Color(0xFFBDBDBD)
            "Marah" -> Color(0xFFFF1744)
            else -> Color(0xFF9E9E9E)
        }
    }

    fun getGradientColorsByLabel(label: String): Pair<Color, Color> {
        return when (label) {
            "Senang" -> Pair(Color(0xFF69F0AE).copy(alpha = 0.5f), Color.Transparent)
            "Sedih" -> Pair(Color(0xFF64B5F6).copy(alpha = 0.5f), Color.Transparent)
            "Netral" -> Pair(Color(0xFFE0E0E0).copy(alpha = 0.5f), Color.Transparent)
            "Marah" -> Pair(Color(0xFFFF5252).copy(alpha = 0.5f), Color.Transparent)
            else -> Pair(Color(0xFF9E9E9E).copy(alpha = 0.5f), Color.Transparent)
        }
    }
}

