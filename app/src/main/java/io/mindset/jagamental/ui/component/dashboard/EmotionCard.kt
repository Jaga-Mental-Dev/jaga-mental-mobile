package io.mindset.jagamental.ui.component.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.mindset.jagamental.R


@Composable
fun EmotionCard(modifier: Modifier = Modifier) {
    val cardBg = Brush.verticalGradient(listOf(Color(0xFFB3C08D), Color(0xFFA0B070)))
    Box(
        modifier =
        modifier
            .background(cardBg, shape = RoundedCornerShape(8.dp)),
    ) {
        Image(
            painter = painterResource(id = R.drawable.solar__face_scan_circle_bold),
            contentDescription = "Account Circle",
            modifier =
            Modifier
                .size(90.dp)
                .offset(y = 12.dp, x = 20.dp)
                .align(Alignment.BottomEnd),
            alpha = 0.2f,
            contentScale = ContentScale.Crop,
        )

        Row(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.solar__face_scan_circle_bold),
                contentDescription = "Account Circle",
                modifier =
                Modifier
                    .size(48.dp)
                    .padding(4.dp),
                tint = Color.White,
            )

            Column(
                modifier = Modifier.padding(start = 8.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(R.string.analize_your_emotion),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                )
                Text(
                    text = stringResource(R.string.analize_your_emotion),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                )
            }
        }
    }
}