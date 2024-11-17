package io.mindset.jagamental.ui.screen.journal.add.preview

import android.graphics.BitmapFactory
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.mindset.jagamental.navigation.Screen
import io.mindset.jagamental.utils.StatusBarColorHelper
import java.io.File
import java.net.URI

@Composable
fun ResultPreviewScreen(
    photoUri: String,
    navController: NavController,
) {
    val bitmap = remember(photoUri) {
        BitmapFactory.decodeFile(File(URI(photoUri)).path)
    }
    val capturedPhoto: ImageBitmap = bitmap.asImageBitmap()

    StatusBarColorHelper(color = Color.Transparent, useDarkIcon = false)
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
                    val emot = "Angry"
                    navController.navigate(Screen.App.PhotoResultScreen(photoUri, emot.lowercase())) {
                        popUpTo(Screen.App.AddCapture) { inclusive = false }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF194A47).copy(alpha = 0.6f),
                ),
                shape = RoundedCornerShape(30),
                border = BorderStroke(1.dp, Color(0xFFE2E7D4).copy(alpha = 0.5f))
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ResultPreviewScreenPreview() {
    ResultPreviewScreen(
        photoUri = "",
        navController = rememberNavController()
    )
}