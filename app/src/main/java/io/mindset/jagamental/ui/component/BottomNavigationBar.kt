package io.mindset.jagamental.ui.component

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import io.mindset.jagamental.ui.theme.tertiaryContainerLightHighContrast

@SuppressLint("AutoboxingStateCreation")
@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Journal,
        BottomNavigationItem.Profile,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.substringAfterLast('.')


    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.drawBehind {
            val borderHeight = 2.dp.toPx()
            drawLine(
                color = Color(0xFFE9EAEB),
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = borderHeight
            )
        },
    ) {

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route::class.simpleName,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    if (currentRoute == item.route::class.simpleName) {
                        Icon(
                            painter = painterResource(item.selectedIcon),
                            contentDescription = item.title
                        )
                    } else {
                        Icon(
                            painter = painterResource(item.unselectedIcon),
                            contentDescription = item.title
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = tertiaryContainerLightHighContrast,
                    unselectedIconColor = Color.LightGray
                ),
                label = {
                    Text(
                        text = item.title,
                        color = if (currentRoute == item.route::class.simpleName) tertiaryContainerLightHighContrast else Color.LightGray,
                        fontSize = 12.sp
                    )
                }
            )
        }
    }
}