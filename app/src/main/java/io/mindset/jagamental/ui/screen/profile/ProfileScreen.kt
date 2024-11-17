package io.mindset.jagamental.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import io.mindset.jagamental.navigation.Screen
import io.mindset.jagamental.utils.StatusBarColorHelper
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(navController: NavController, paddingValues: PaddingValues) {

    val viewModel: ProfileViewModel = koinViewModel()
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    StatusBarColorHelper(Color.White, useDarkIcon = true)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            Text(
                text = "Profile!",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 32.sp
                )
            )

            Button(
                onClick = {
                    viewModel.logout()
                    navController.navigate(Screen.Auth.Login) {
                        popUpTo(Screen.App.Dashboard) { inclusive = true }
                    }
                }
            ) {
                Text("Logout")
            }
        }
    }
}