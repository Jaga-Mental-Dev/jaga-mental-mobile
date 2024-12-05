package io.mindset.jagamental.ui.component.dashboard

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import io.mindset.jagamental.R
import io.mindset.jagamental.data.model.ProfessionalProfile
import io.mindset.jagamental.ui.theme.primaryColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfesionalCard(
    data: ProfessionalProfile,
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .size(width = 200.dp, height = 360.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Column(
            Modifier.padding(8.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp)
                    .background(
                        Color.LightGray.copy(0.3f), RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
                    .clip(RoundedCornerShape(8.dp)),
                model = data.avatar,
                placeholder = painterResource(R.drawable.profile_unselected),
                error = painterResource(R.drawable.profile_unselected),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.size(4.dp))
            Text(
                text = data.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.size(4.dp))
            Text(
                text = data.specialist,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.size(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Rounded.LocationOn,
                    contentDescription = null,
                    tint = Color.Gray
                )
                Spacer(Modifier.size(4.dp))
                Text(
                    text = data.city,
                    fontSize = 12.sp
                )
            }

            Spacer(Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor
                ),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    val intent =
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/${data.phone}"))
                    context.startActivity(intent)
                }
            ) {
                Text(
                    text = "Book",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}