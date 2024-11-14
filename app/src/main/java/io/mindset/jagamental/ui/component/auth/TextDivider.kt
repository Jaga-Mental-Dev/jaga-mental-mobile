package io.mindset.jagamental.ui.component.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.mindset.jagamental.ui.theme.gray200
import io.mindset.jagamental.ui.theme.gray400

@Composable
fun TextDivider(modifier: Modifier, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .height(2.dp),
            color = gray200
        )

        Text(
            text = text,
            style = TextStyle(
                color = gray400,
                fontSize = 14.sp
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .height(2.dp),
            color = gray200
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextDividerPreview() {
    Column(
        modifier = Modifier.height(100.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextDivider(modifier = Modifier,"Login with")
    }
}