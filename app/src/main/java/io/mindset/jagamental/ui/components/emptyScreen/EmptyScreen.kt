package io.mindset.jagamental.ui.components.emptyScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.mindset.jagamental.R
import io.mindset.jagamental.ui.components.newJournal.JournalButton

@Composable
fun EmptyScreen(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Centers children horizontally in the Column
            verticalArrangement = Arrangement.Center // Centers children vertically in the Column
        ) {
            Image(painter = painterResource(R.drawable.add_note), contentDescription = null)

            Text(
                text = "Belum Ada Jurnal",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(top = 24.dp)
            )

            Text(
                text = "Yuk tulis jurnal tentang apapun yang kamu rasain saat ini",
                fontSize = 16.sp,
                color = Color(0xFF72767F),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .padding(horizontal = 22.dp)
            )

            JournalButton(
                onclick = onClick,
                modifier = Modifier
                    .padding(vertical = 24.dp)
            )
        }
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewScreen() {
    EmptyScreen(onClick = { })
}