package io.mindset.jagamental.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.mindset.jagamental.R

@Composable
fun OAuthButton(onclick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        modifier = modifier
            .width(103.dp)
            .height(40.dp),
        onClick = onclick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5F5)),
        shape = RoundedCornerShape(size = 12.dp),
        border = BorderStroke(1.dp, Color(0xFFE9EAEB)))
    {
        Image(modifier = Modifier, painter = painterResource(id = R.drawable.google_icon), contentDescription = null)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun OAuthButtonPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        OAuthButton(onclick = {})
    }
}