package io.mindset.jagamental.ui.component.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.mindset.jagamental.R

@Composable
fun DashboardHeader() {
    Box(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(142.dp)
            .clip(RectangleShape)
            .background(color = Color(0xFF194A47)),
    ) {
        Image(
            painter = painterResource(id = R.drawable.people_nearby),
            contentDescription = "Mindset Logo",
            modifier =
            Modifier
                .padding(end = 16.dp, top = 32.dp)
                .align(Alignment.CenterEnd)
                .scale(3f)
                .height(600.dp),
            contentScale = ContentScale.Inside,
        )

        Column(
            modifier =
            Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Text(
                text = stringResource(R.string.welcome),
                color = Color.White,
                fontSize = 20.sp,
            )

            Text(
                text = stringResource(R.string.dummy_name),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}