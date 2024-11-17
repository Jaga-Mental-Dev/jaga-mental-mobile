package io.mindset.jagamental.ui.screen.dashboard

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.mindset.jagamental.R
import io.mindset.jagamental.utils.StatusBarColorHelper
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line

@Composable
fun DashboardScreen(navController: NavController, paddingValues: PaddingValues) {

    val modifiedPaddingValues = PaddingValues(
        start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
        top = 0.dp,
        end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
        bottom = paddingValues.calculateBottomPadding()
    )

    StatusBarColorHelper(Color.Transparent, useDarkIcon = false)

    Column(
        modifier = Modifier
            .padding(modifiedPaddingValues)
            .padding(top = 0.dp)
            .fillMaxSize()
            .background(color = Color.White)
    ) {
            DashboardHeader()

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState()),
            ) {
                Text(
                    text = "Dashboard",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                            .height(140.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, Color(0xFFE8EDED), shape = RoundedCornerShape(8.dp)),
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_solar_document),
                                contentDescription = "Account Circle",
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(Color(0xFFD1DBDA), shape = RoundedCornerShape(8.dp))
                                    .padding(4.dp),
                            )

                            Text(
                                text = "Chatbot",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                            .height(140.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, Color(0xFFE8EDED), shape = RoundedCornerShape(8.dp)),
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_solar_document),
                                contentDescription = "Account Circle",
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(Color(0xFFD1DBDA), shape = RoundedCornerShape(8.dp))
                                    .padding(4.dp),
                            )

                            Text(
                                text = "Journal",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Text(
                    text = "Apa yang kamu rasakan?",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(16.dp)
                )

                EmotionCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(73.dp)
                        .clip(RectangleShape)
                )

                Text(
                    text = "Mindful Tracker",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(top = 20.dp)
                )

                MoodChart()
            }
    }
}

@Composable
fun DashboardHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(142.dp)
            .clip(RectangleShape)
            .background(color = Color(0xFF194A47)),
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

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {

            Text(
                text = "Selamat Datang,",
                color = Color.White,
                fontSize = 20.sp
            )

            Text(
                text = "Ahmad Fauzan Wiratama Pratama Sujatmiko Rizky Saputro",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun EmotionCard(modifier: Modifier = Modifier) {
    val cardBg = Brush.verticalGradient(listOf(Color(0xFFB3C08D), Color(0xFFA0B070)))
    Box(
        modifier = modifier
            .background(cardBg, shape = RoundedCornerShape(8.dp)),
    ) {
        Image(
            painter = painterResource(id = R.drawable.solar__face_scan_circle_bold),
            contentDescription = "Account Circle",
            modifier = Modifier
                .size(90.dp)
                .offset(y = 12.dp, x = 20.dp)
                .align(Alignment.BottomEnd),
            alpha = 0.2f,
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.solar__face_scan_circle_bold),
                contentDescription = "Account Circle",
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp),
                tint = Color.White
            )

            Column(
                modifier = Modifier.padding(start = 8.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Analize your emotion",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Text(
                    text = "Analize your emotion",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun MoodChart() {
    LineChart(
        modifier = Modifier.fillMaxWidth()
            .height(240.dp)
            .padding(horizontal = 16.dp)
            .padding(bottom = 30.dp),
        data = remember {
            listOf(
                Line(
                    label = "Senang",
                    values = listOf(2.0, 4.0, 3.0, 2.0, 3.0),
                    color = SolidColor(Color(0xFF23af92)),
                    firstGradientFillColor = Color(0xFF2BC0A1).copy(alpha = .5f),
                    secondGradientFillColor = Color.Transparent,
                    strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                    gradientAnimationDelay = 1000,
                    drawStyle = DrawStyle.Stroke(width = 2.dp),
                ),
                Line(
                    label = "Sedih",
                    values = listOf(4.0, 2.0, 8.0, 5.0, 5.0),
                    color = SolidColor(Color.Blue),
                    firstGradientFillColor = Color(0xFF2BC0A1).copy(alpha = .5f),
                    secondGradientFillColor = Color.Transparent,
                    strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                    gradientAnimationDelay = 1000,
                    drawStyle = DrawStyle.Stroke(width = 2.dp),
                )
            )
        },
        animationMode = AnimationMode.Together(delayBuilder = {
            it * 500L
        }),
        gridProperties = GridProperties(enabled = false),
        indicatorProperties = HorizontalIndicatorProperties(
            enabled = true,
            contentBuilder = { value ->
                value.toInt().toString()
            }
        ),
        labelProperties = LabelProperties(
            enabled = true,
            labels = listOf("Sun", "Mon", "Wed", "Thru", "Fri", "Sat")
        )
    )
}
