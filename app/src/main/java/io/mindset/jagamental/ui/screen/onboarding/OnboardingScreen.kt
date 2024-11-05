package io.mindset.jagamental.ui.screen.onboarding

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.mindset.jagamental.navigation.Route

@Composable
fun OnboardingScreen(navController: NavController) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Onboarding")
            Button(
                onClick = {
                    navController.navigate(Route.Login)
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Login")
            }
        }
    }
}
