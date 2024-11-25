package io.mindset.jagamental.ui.component.journal

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import io.mindset.jagamental.R

@Composable
fun AnalyzeMoodLoading(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.camera_capturing))

    LottieAnimation(
        modifier = Modifier.size(200.dp),
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
}