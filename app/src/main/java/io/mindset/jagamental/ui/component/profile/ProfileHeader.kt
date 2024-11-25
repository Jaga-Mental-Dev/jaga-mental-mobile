package io.mindset.jagamental.ui.component.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import io.mindset.jagamental.R

@Composable
fun ProfileHeader(
    userName: String?,
    userEmail: String?,
    userPhotoUrl: String?
) {
    Box(
        modifier = Modifier
            .clip(RectangleShape)
            .background(color = Color(0xFF194A47))
            .height(272.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.people_nearby),
            contentDescription = "Mindset Logo",
            modifier = Modifier
                .padding(end = 16.dp, top = 32.dp)
                .align(Alignment.CenterEnd)
                .scale(3f)
                .height(600.dp),
            contentScale = ContentScale.Inside
        )

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
            ),
            shape = RectangleShape
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.LightGray, CircleShape)
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize(),
                            model = userPhotoUrl,
                            contentDescription = "Profile Image",
                            contentScale = ContentScale.Crop,
                            error = painterResource(id = R.drawable.solar__face_scan_circle_bold),
                            placeholder = painterResource(id = R.drawable.solar__face_scan_circle_bold),
                        )
                    }
                }

                Text(
                    text = userName ?: stringResource(R.string.unknown),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 2.dp)
                        .padding(top = 16.dp)
                )

                Text(
                    text = userEmail ?: stringResource(R.string.unknown),
                    style = TextStyle(
                        color = Color.LightGray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
                )
            }
        }
    }
}