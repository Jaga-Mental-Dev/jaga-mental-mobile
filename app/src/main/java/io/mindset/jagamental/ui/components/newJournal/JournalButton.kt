package io.mindset.jagamental.ui.components.newJournal

import android.widget.Toast
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.mindset.jagamental.R

@Composable
fun JournalButton(onclick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF194A47)),
        onClick = onclick,
        modifier = modifier
            .padding(16.dp)
            .height(48.dp)
    ) {
        Row {
            Icon(painter = painterResource(R.drawable.pencil), contentDescription = null)

            Text(
                text = "Tulis Jurnal Baru", modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}