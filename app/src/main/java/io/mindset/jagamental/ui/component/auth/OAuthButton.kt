package io.mindset.jagamental.ui.component.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
