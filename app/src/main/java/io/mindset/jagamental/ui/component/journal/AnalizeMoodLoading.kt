package io.mindset.jagamental.ui.component.journal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import io.mindset.jagamental.R

@Composable
fun AnalyzeMoodLoading(text: String) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.mood_analyze))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        LottieAnimation(
            modifier = Modifier
                .size(300.dp)
                .scale(1.5f),
            composition = composition,
            iterations = LottieConstants.IterateForever,
        )
        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, device = "id:pixel_8")
@Composable
fun AnalyzeMoodLoadingPreview() {
    AnalyzeMoodLoading("Tunggu sebentar ya\nKami sedang menganalisa mood kamu...")
}