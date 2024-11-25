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
import org.koin.androidx.compose.koinViewModel

@Composable
fun RootScreen() {
    val viewModel: RootViewModel = koinViewModel()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val bottomBarExcludedRoutes = listOf(
        Screen.Auth.Login::class.simpleName,
        Screen.Auth.Register::class.simpleName,
        Screen.App.CapturePhotoScreen::class.simpleName,
        Screen.App.ResultPreviewScreen::class.simpleName,
        Screen.App.InputJournalScreen::class.simpleName,
        Screen.App.PhotoResultScreen::class.simpleName
    )

    val isIncludeRoute = bottomBarExcludedRoutes.none {
        it?.let { it1 -> navBackStackEntry?.destination?.route?.contains(it1) } == true
    }

    BackActionHandler(navController = navController)
    Scaffold(
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