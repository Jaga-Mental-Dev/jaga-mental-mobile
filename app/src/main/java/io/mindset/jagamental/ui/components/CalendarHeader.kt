package io.mindset.jagamental.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun CalendarHeader(
    onDateClick: (formattedDate: String) -> Unit
) {
    val currentDate = LocalDate.now()
    val datesOfWeek = (-3..3).map { currentDate.plusDays(it.toLong()) }

    var selectedDate by remember { mutableStateOf(currentDate) }

    val dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

    val formattedSelectedDate = selectedDate.format(dateFormatter)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF194A47))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 24.dp
                ),
                text = "Journal Timeline",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight(600)
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                datesOfWeek.forEach { date ->
                    val isSelected = date == selectedDate

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                            .clickable {
                                selectedDate = date
                                onDateClick(formattedSelectedDate)
                            },
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) Color.White else Color(0xFF476E6C)
                            ),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .padding(4.dp),
                                verticalArrangement = Arrangement.SpaceAround
                            ) {
                                Text(
                                    text = date.dayOfWeek.name.take(3),
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        color = if (isSelected) Color.Black else Color.White
                                    )
                                )
                                Text(
                                    text = date.dayOfMonth.toString(),
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        color = if (isSelected) Color.Black else Color.White
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}