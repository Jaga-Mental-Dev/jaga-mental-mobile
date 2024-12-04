package io.mindset.jagamental.ui.component.journal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.mindset.jagamental.data.model.response.JournalData
import io.mindset.jagamental.utils.EmotionHelper
import java.time.LocalDateTime

@Composable
fun JournalListItem(
    modifier: Modifier = Modifier,
    journal: JournalData,
    onItemClick: () -> Unit
) {
    val emotionHelper = EmotionHelper()

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .border(1.dp, Color(0xFFE8EDED), shape = RoundedCornerShape(8.dp))
            .clickable(onClick = onItemClick),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE8EDED))
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(24.dp),
                        painter = painterResource(
                            id = emotionHelper.getEmotionIcon(
                                journal.emotion ?: "netral"
                            )
                        ),
                        contentDescription = "Emotion Icon",
                    )

                    val time = LocalDateTime.parse(journal.createdAt)
                    Text(
                        text = "${journal.emotion?.replaceFirstChar { it.uppercase() } ?: "Tidak ada emosi"} - ${time.hour}:${time.minute}",
                        modifier = Modifier.padding(8.dp),
                        style = TextStyle(
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    )
                }
            }

            Column(
                modifier = Modifier.padding(12.dp),
            ) {
                Text(
                    text = journal.title ?: "Tidak ada judul",
                    modifier = Modifier.padding(bottom = 4.dp),
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight(600),
                        fontSize = 18.sp
                    )
                )

                Text(
                    text = journal.content ?: "Tidak ada deskripsi",
                    style = TextStyle(
                        color = Color(0xFF72767F),
                        fontSize = 14.sp,
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}