package io.mindset.jagamental.ui.screen.login

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
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
import io.mindset.jagamental.navigation.Route
import io.mindset.jagamental.ui.components.FilledButton
import io.mindset.jagamental.ui.components.GoogleSignInButton
import io.mindset.jagamental.ui.components.RoundedTextField
import io.mindset.jagamental.ui.components.TextDivider
import io.mindset.jagamental.ui.theme.gray50
import io.mindset.jagamental.ui.theme.tertiaryContainerLightHighContrast

@Composable
fun LoginScreen(navController: NavHostController) {

    val scrollState = rememberScrollState()
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ) {
            val configuration = LocalConfiguration.current
            val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            val screenWidth = configuration.screenWidthDp.dp

            Image(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .fillMaxHeight(if (!isLandscape) 0.8f else 0.8f)
                    .fillMaxWidth(if (!isLandscape) 0.8f else 0.8f)
                    .let { modifier ->
                        if (isLandscape) {
                            modifier.offset(x = screenWidth * 0.28f)
                        } else {
                            modifier
                        }
                    },
                painter = painterResource(id = R.drawable.solar_people_nearby_bold_duotone),
                contentDescription = null,
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .verticalScroll(scrollState),
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
                        stringResource(id = R.string.or_label),
                    )

                    GoogleSignInButton(
                        onClick = {}
                    )
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(gray50)
                    .padding(bottom = 10.dp),
            ) {
                Row(
                    Modifier
                        .height(40.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.no_account),
                        style = TextStyle(
                            fontWeight = FontWeight.Normal
                        )
                    )
                    TextButton(
                        onClick = {
                            navController.navigate(Route.Register)
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = tertiaryContainerLightHighContrast),
                        modifier = Modifier.offset(x = (-8).dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.register_now),
                            style = TextStyle(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
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