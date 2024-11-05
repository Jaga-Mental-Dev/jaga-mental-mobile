package io.mindset.jagamental.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.mindset.jagamental.ui.theme.gray200
import io.mindset.jagamental.ui.theme.gray50
import io.mindset.jagamental.ui.theme.primaryLightHighContrast

@Composable
fun RoundedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    type: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        visualTransformation = if (type == "password") PasswordVisualTransformation() else VisualTransformation.None,
        placeholder = { Text(text = placeholder) },
        shape = RoundedCornerShape(16.dp),
        textStyle = TextStyle(
            fontSize = 14.sp
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = primaryLightHighContrast,
            unfocusedBorderColor = gray200,
            unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
            focusedLabelColor = MaterialTheme.colorScheme.secondary,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedContainerColor = gray50,
            unfocusedContainerColor = gray50,
        ),
        modifier = modifier
            .fillMaxWidth()
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun RoundedTextFieldPreview() {
    Column {
        RoundedTextField(
            value = "test@mail.com",
            onValueChange = {},
            label = "Email",
            placeholder = "Enter your email"
        )
    }
}