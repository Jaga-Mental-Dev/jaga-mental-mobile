package io.mindset.jagamental.ui.screen.journal.result

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import io.mindset.jagamental.R
import io.mindset.jagamental.navigation.Screen
import io.mindset.jagamental.ui.theme.primaryColor
import io.mindset.jagamental.utils.EmotionHelper
import io.mindset.jagamental.utils.LockScreenOrientation
import io.mindset.jagamental.utils.StatusBarColorHelper
import io.mindset.jagamental.utils.UiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun JournalResultScreen(
    journalId: String, navController: NavController
) {
    val viewModel: JournalResultViewModel = koinViewModel()
    val journalState = viewModel.journalState.collectAsState()
    val scrollState = rememberScrollState()
    val placeHolderImage = "https://placehold.co/600x400?text=Oops!"

    BackHandler {
        navController.navigate(Screen.App.MainJournalScreen) {
            popUpTo(Screen.App.MainJournalScreen) { inclusive = true }
            launchSingleTop = true
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getJournalById(journalId)
        viewModel.saveJournalStatus(journalId, true)
    }

    StatusBarColorHelper(
        color = Color.Transparent, useDarkIcon = false, navBarColor = Color.Transparent
    )
    LockScreenOrientation()
    when (val state = journalState.value) {

        is UiState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            primaryColor, shape = RoundedCornerShape(
                                bottomStart = 16.dp, bottomEnd = 16.dp
                            )
                        )
                        .padding(16.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .scale(3f)
                            .align(Alignment.CenterEnd),
                        painter = painterResource(R.drawable.people_nearby),
                        contentDescription = null,
                    )
                    Column(
                        modifier = Modifier.padding(top = 24.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "Hasil Analisa",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )

                            Box(
                                modifier = Modifier.background(
                                    Color.LightGray, shape = RoundedCornerShape(8.dp)
                                ), contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    modifier = Modifier.padding(
                                        horizontal = 12.dp, vertical = 2.dp
                                    ),
                                    text = "4/4",
                                    color = primaryColor,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }

                ResultCard(
                    imageUrl = state.data?.imageUrl ?: placeHolderImage,
                    emotionResult = state.data?.emotion ?: "",
                    feedback = state.data?.feedback ?: "Saran tidak tersedia"
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {

                    Text(
                        text = "Chatbot",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                painter = painterResource(R.drawable.chatbot_unselected),
                                contentDescription = null,
                                tint = Color.DarkGray
                            )
                            Spacer(Modifier.size(24.dp))

                            Text(
                                text = "Masih butuh teman curhat?\nYuk cobain ngobrol sama JagaBot!",
                                textAlign = TextAlign.Start,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                        Spacer(Modifier.size(16.dp))

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryColor
                            ),
                            shape = RoundedCornerShape(16.dp),
                            onClick = {}
                        ) {
                            Text(
                                text = "Coba Ngobrol",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .background(color = primaryColor)
                        .height(420.dp)
                        .clip(RectangleShape)
                        .fillMaxWidth(),
                ) {
                    Image(
                        modifier = Modifier
                            .scale(2f)
                            .offset(y = 24.dp, x = 24.dp)
                            .align(Alignment.CenterEnd),
                        painter = painterResource(R.drawable.people_nearby),
                        contentDescription = null,
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = "Psikolog Rekomendasi",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Spacer(Modifier.size(12.dp))

                        LazyRow(
                            modifier = Modifier.height(360.dp),
                            contentPadding = PaddingValues(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            items(
                                count = 3,
                                itemContent = {
                                    PsikologCard()
                                }
                            )
                        }
                    }
                }
            }
        }

        is UiState.Error -> {
            Log.i("JournalResultScreen", "Error: ${state.message}")
        }

        is UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = primaryColor,
                    strokeWidth = 4.dp
                )
            }
        }

        is UiState.Idle -> {}
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PsikologCard() {
    Spacer(Modifier.size(8.dp))
    Card(
        modifier = Modifier
            .size(width = 200.dp, height = 360.dp)
            .padding(),
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
                    .clip(RoundedCornerShape(8.dp)),
                model = "https://randomuser.me/api/portraits/women/29.jpg",
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.size(8.dp))

            Text(
                text = "John Doe",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.size(4.dp))
            Text(
                text = "Psikolog",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
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
                    text = "Jakarta",
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
                onClick = {}
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

@Composable
private fun ResultCard(
    imageUrl: String,
    emotionResult: String,
    feedback: String
) {
    val emotionHelper = EmotionHelper()
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(0.9f)
            .offset(y = (-80).dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(shape = RoundedCornerShape(12.dp)),
                    clipToBounds = true
                )
                Spacer(Modifier.size(12.dp))
                Column {
                    Text(
                        text = "Hasil analisa kamu", fontSize = 12.sp, color = Color.Gray
                    )
                    Text(
                        text = emotionResult.replaceFirstChar { it.uppercase() },
                        fontSize = 24.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(Modifier.weight(1f))
                val emotionIcon = emotionHelper.getEmotionIcon(emotionResult)
                Image(
                    painter = painterResource(emotionIcon),
                    contentDescription = null,
                )
            }
            Spacer(Modifier.size(8.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = feedback,
                textAlign = TextAlign.Center
            )
        }

    }
}

@Composable
private fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun JournalResultScreenPreview() {
    JournalResultScreen(
        journalId = "preview_id", navController = rememberNavController()
    )
}