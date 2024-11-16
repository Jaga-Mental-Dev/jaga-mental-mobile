package io.mindset.jagamental.ui.screen.journal.add.photoresult

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.mindset.jagamental.R
import io.mindset.jagamental.ui.component.TopBar
import io.mindset.jagamental.utils.StatusBarColorHelper
import io.mindset.jagamental.utils.getBackGroundColorByEmotion
import java.io.File
import java.net.URI

@Composable
fun PhotoResultScreen(
    photoUri: String,
    emotion: String,
    navController: NavController
) {

    val bitmap = remember(photoUri) {
        BitmapFactory.decodeFile(File(URI(photoUri)).path)
    }
    val capturedPhoto: ImageBitmap = bitmap.asImageBitmap()
    val pageBgColor = getBackGroundColorByEmotion(emotion)

    StatusBarColorHelper(color = Color.Transparent, useDarkIcon = true)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(pageBgColor),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            TopBar(
                title = "Photo Result",
                titleColor = Color.Black,
                containerColor = pageBgColor,
                onBack = { navController.popBackStack() }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .padding(horizontal = 16.dp, vertical = 50.dp)
                    .background(Color.Transparent)
                    .clip(RoundedCornerShape(16.dp)),
            ) {
                Image(
                    bitmap = capturedPhoto,
                    contentDescription = "Captured photo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.smiling_face_with_smiling_eyes),
                        contentDescription = "Emotion Result",
                        modifier = Modifier.size(50.dp),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = "Wow, you seem super happy! Let’s capture that energy in your journal!",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Row {
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF194A47),
                ),
                shape = MaterialTheme.shapes.medium
            ) { Text(
                text = "Next",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )}
        }
    }
}