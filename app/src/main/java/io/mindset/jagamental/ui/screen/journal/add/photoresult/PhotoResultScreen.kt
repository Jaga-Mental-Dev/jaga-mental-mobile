package io.mindset.jagamental.ui.screen.journal.add.photoresult

import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import io.mindset.jagamental.R
import io.mindset.jagamental.ui.component.TopBar
import io.mindset.jagamental.utils.EmotionHelper
import io.mindset.jagamental.utils.StatusBarColorHelper
import io.mindset.jagamental.utils.getBackGroundColorByEmotion
import java.io.File
import java.net.URI

@Composable
fun PhotoResultScreen(
    photoUri: String,
    emotion: String,
    words: String,
    photoUrl: String,
    navController: NavController
) {

    val bitmap = remember(photoUri) {
        BitmapFactory.decodeFile(File(URI(photoUri)).path)
    }
    val context = LocalContext.current

    val emotionHelper = EmotionHelper()
    val capturedPhoto: ImageBitmap = bitmap.asImageBitmap()
    val pageBgColor = getBackGroundColorByEmotion(emotion)
    val emotionIcon = emotionHelper.getEmotionIcon(emotion)

    StatusBarColorHelper(color = Color.Transparent, useDarkIcon = true)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(pageBgColor),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            TopBar(
                title = stringResource(R.string.photo_result),
                titleColor = Color.Black,
                containerColor = pageBgColor,
                onBack = { navController.popBackStack() },
                action = {
                    Box(
                        modifier = Modifier.padding(horizontal = 12.dp)
                    ){
                        Row {
                            Text(stringResource(R.string.input_step_2_3))
                        }
                    }
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp, top = 8.dp)
                    .background(Color.Transparent)
                    .clip(RoundedCornerShape(16.dp)),
            ) {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = stringResource(R.string.captured_photo),
                    placeholder = painterResource(R.drawable.placeholder_image),
                    error = painterResource(R.drawable.image_error),
                    onError = {
                        Toast.makeText(context, "Failed to load image", Toast.LENGTH_SHORT).show()
                    },
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
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
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(emotionIcon),
                        contentDescription = stringResource(R.string.emotion_result),
                        modifier = Modifier.size(80.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = words,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
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
                ) {
                    Text(
                        text = stringResource(R.string.next),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                }
            }
        }
    }
}