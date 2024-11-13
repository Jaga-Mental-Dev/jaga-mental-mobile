package io.mindset.jagamental.ui.components.bottombar

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController

@SuppressLint("AutoboxingStateCreation")
@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Journal,
        BottomNavigationItem.Profile,
    )

    val selectedItemIndex = rememberSaveable{
        mutableStateOf(0)
    }

    NavigationBar() {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex.value == index,
                onClick = {
                    selectedItemIndex.value = index
                    navController.navigate(item.route)
                },
                icon = {
                    if (selectedItemIndex.value == index) {
                        Icon(item.selectedIcon, contentDescription = item.title)
                    } else {
                        Icon(item.unselectedIcon, contentDescription = item.title)
                    }
                }
            )
        }
    }
}