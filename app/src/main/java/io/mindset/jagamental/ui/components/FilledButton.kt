package io.mindset.jagamental.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FilledButton(name: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(size = 16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF194A47))
    ) {
        Text(
            text = name,
            style = TextStyle(
                fontSize = 16.sp
            )
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ButtonPreview() {
    Column {
        FilledButton(name = "Login", onClick = {})
        FilledButton(name = "Register", onClick = {})
    }
}