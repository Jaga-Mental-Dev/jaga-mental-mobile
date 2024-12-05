package io.mindset.jagamental.ui.screen.root

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.mindset.jagamental.navigation.RootNavGraph
import io.mindset.jagamental.navigation.Screen
import io.mindset.jagamental.ui.component.BottomNavigationBar
import io.mindset.jagamental.ui.component.util.BackActionHandler

@Composable
fun RootScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val includedRoutes = listOf(
        Screen.App.Dashboard::class.simpleName,
        Screen.App.Profile::class.simpleName,
        Screen.App.MainJournalScreen::class.simpleName
    )

    val isIncludeRoute = includedRoutes.any {
        it?.let { it1 -> navBackStackEntry?.destination?.route?.contains(it1) } == true
    }

    BackActionHandler(navController = navController)
    Scaffold(
        containerColor = Color.White,
        modifier = Modifier.background(color = Color.White),
        bottomBar = {
            AnimatedVisibility(
                visible = isIncludeRoute,
                enter = slideInVertically(
                    initialOffsetY = { it }
                ),
            ) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        RootNavGraph(
            navController = navController,
            paddingValues = innerPadding
        )
    }
}