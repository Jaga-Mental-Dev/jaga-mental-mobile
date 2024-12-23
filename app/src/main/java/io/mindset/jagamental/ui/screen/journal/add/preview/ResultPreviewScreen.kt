package io.mindset.jagamental.ui.screen.journal.add.preview

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.mindset.jagamental.navigation.Screen
import io.mindset.jagamental.ui.component.journal.AnalyzeMoodLoading
import io.mindset.jagamental.utils.StatusBarColorHelper
import io.mindset.jagamental.utils.UiState
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.net.URI

@Composable
fun ResultPreviewScreen(
    photoUri: String,
    navController: NavController,
) {
    val viewModel: ResultPreviewViewModel = koinViewModel()
    val bitmap = remember(photoUri) {
        BitmapFactory.decodeFile(File(URI(photoUri)).path)
    }
    val capturedPhoto: ImageBitmap = bitmap.asImageBitmap()

    val journalState = viewModel.journalState.collectAsState()
    val suggestion = viewModel.suggestion.collectAsState().value
    var hasNavigated by remember { mutableStateOf(false) }

    StatusBarColorHelper(color = Color.Transparent, useDarkIcon = false)

    Log.d("ResultPreviewScreen", "CurrentState: ${journalState.value}")
    when (val state = journalState.value) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                AnalyzeMoodLoading("Tunggu sebentar ya\nKami sedang menganalisa mood kamu...")
            }
        }

        is UiState.Success -> {
            val emotionResult = state.data.initialEmotion.toString()
            val photoUrl = state.data.imageUrl.toString()
            val journalId = state.data.id.toString()
            if (!hasNavigated) {
                hasNavigated = true
                navController.navigate(
                    Screen.App.PhotoResultScreen(
                        journalId,
                        emotionResult,
                        suggestion,
                        photoUrl,
                    )
                ) {
                    launchSingleTop = true
                }
            }
        }

        is UiState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Error: ${state.message}", color = Color.White)
                    Button(onClick = { viewModel.convertToByteArray(photoUri) }) {
                        Text(text = "Retry")
                    }
                }
            }
        }

        is UiState.Idle -> {
            Box {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Black),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        bitmap = capturedPhoto,
                        contentDescription = "Last captured photo",
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 50.dp)
                        .align(Alignment.BottomCenter),
                ) {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 10.dp),
                        onClick = {
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.LightGray.copy(alpha = 0.5f),
                        ),
                        shape = RoundedCornerShape(30),
                        border = BorderStroke(1.dp, Color(0xFFE2E7D4).copy(alpha = 0.5f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }

                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp),
                        onClick = {
                            viewModel.convertToByteArray(photoUri)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF194A47).copy(alpha = 0.6f),
                        ),
                        shape = RoundedCornerShape(30),
                        border = BorderStroke(1.dp, Color(0xFFE2E7D4).copy(alpha = 0.5f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Confirm",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}
