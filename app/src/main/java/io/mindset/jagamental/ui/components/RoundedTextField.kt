package io.mindset.jagamental.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RoundedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun RoundedTextFieldPreview() {
    Column {
        RoundedTextField(
            value = "",
            onValueChange = {},
            label = "Email",
            placeholder = "Enter your email"
        )

        RoundedTextField(
            value = "",
            onValueChange = {},
            label = "Password",
            placeholder = ""
        )
    }
}