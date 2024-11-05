package io.mindset.jagamental.ui.screen.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.mindset.jagamental.R
import io.mindset.jagamental.ui.components.FilledButton
import io.mindset.jagamental.ui.components.OAuthButton
import io.mindset.jagamental.ui.components.RoundedTextField
import io.mindset.jagamental.ui.components.TextDivider

@Composable
fun LoginScreen(navController: NavHostController) {

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 60.dp)
                .size(width = 411.dp, height = 419.dp),
            painter = painterResource(id = R.drawable.solar_people_nearby_bold_duotone),
            contentDescription = null,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
        ) {

            Image(
                painter = painterResource(id = R.mipmap.ic_launcher),
                contentDescription = null,
            )

            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(id = R.string.app_name),
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight(600),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                )
            )

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(id = R.string.login_subtitle),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Start,
                    lineHeight = 24.sp,
                )
            )

            Column(
                modifier = Modifier.padding(top = 40.dp),
            ) {

                RoundedTextField(
                    modifier = Modifier,
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = stringResource(id = R.string.login_email_label),
                    placeholder = stringResource(id = R.string.login_email_placeholder),
                )

                RoundedTextField(
                    modifier = Modifier.padding(top = 16.dp),
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = stringResource(id = R.string.login_password_label),
                    placeholder = "",
                    type = "password"
                )

                FilledButton(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .height(48.dp),
                    name = stringResource(id = R.string.login),
                    onClick = {
                        Log.d("LoginScreen", "LoginScreen: ${email.value}, ${password.value}")
                    }
                )

                TextDivider(
                    modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                    "Atau Masuk Dengan"
                )

                OAuthButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    onclick = {},
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Belum punya akun? ",
                        style = TextStyle(
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Text(
                        text = "Daftar Sekarang",
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}