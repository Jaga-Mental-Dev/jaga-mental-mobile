package io.mindset.jagamental.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.mindset.jagamental.utils.StatusBarColorHelper

@Composable
fun ProfileScreen(navController: NavController) {
        StatusBarColorHelper()
        Column(
            modifier = Modifier
                .padding()
                .fillMaxSize()
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Profile"
            )
        }
}