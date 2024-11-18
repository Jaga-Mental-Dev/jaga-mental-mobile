package io.mindset.jagamental.data.model

import android.graphics.Color

data class ChartData(
    val emotion: String,
    val values: List<Double>,
    val color: Color,
)