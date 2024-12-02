package io.mindset.jagamental.ui.component.chart

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import io.mindset.jagamental.data.model.ChartData
import io.mindset.jagamental.utils.ColorHelper
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line

@Composable
fun DashLineChart(
    graphData: List<ChartData>,
    modifier: Modifier = Modifier
) {
    LineChart(
        modifier = modifier,
        data = graphData.map {
            val gradientColor = ColorHelper.getGradientColorsByLabel(it.emotion)
            Line(
                label = it.emotion,
                values = it.values,
                color = SolidColor(ColorHelper.getColorByLabel(it.emotion)),
                firstGradientFillColor = gradientColor.first,
                secondGradientFillColor = gradientColor.second,
                strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                gradientAnimationDelay = 1000,
                drawStyle = DrawStyle.Stroke(width = 2.dp),
            )
        },
        animationMode = AnimationMode.Together(delayBuilder = { it * 500L }),
        gridProperties = GridProperties(enabled = false),
        indicatorProperties = HorizontalIndicatorProperties(
            enabled = true,
            contentBuilder = { value -> value.toInt().toString() }
        ),
        labelProperties = LabelProperties(
            enabled = true,
            labels = listOf("Sun", "Mon", "Wed", "Thru", "Fri", "Sat")
        ),
    )
}