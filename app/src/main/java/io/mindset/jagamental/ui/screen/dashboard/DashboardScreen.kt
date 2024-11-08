package io.mindset.jagamental.ui.screen.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import io.mindset.jagamental.navigation.Route
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(navController: NavController) {

    val viewModel: DashboardViewModel = koinViewModel()
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Text(
                    text = "Dashboard!",
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 32.sp
                    )
                )

                Button(
                    onClick = {
                        viewModel.signOut()
                        navController.navigate(Route.Login) {
                            popUpTo(Route.Dashboard) { inclusive = true }
                        }
                    }
                ) {
                    Text("Logout")
                }
            }
        }
    }
}