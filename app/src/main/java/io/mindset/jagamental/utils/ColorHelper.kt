package io.mindset.jagamental.utils

import androidx.compose.ui.graphics.Color

object ColorHelper {
    fun getColorByLabel(label: String): Color {
        return when (label) {
            "Happy" -> Color(0xFF23af92)
            "Sad" -> Color.Blue
            else -> Color.Gray
        }
    }

    fun getGradientColorsByLabel(label: String): Pair<Color, Color> {
        return when (label) {
            "Happy" -> Pair(Color(0xFF2BC0A1).copy(alpha = .5f), Color.Transparent)
            "Sad" -> Pair(Color(0xFF2BC0A1).copy(alpha = .5f), Color.Transparent)
            else -> Pair(Color.Gray, Color.Transparent)
        }
    }
}