package io.mindset.jagamental.ui.component

import io.mindset.jagamental.R
import io.mindset.jagamental.navigation.Screen


sealed class BottomNavigationItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val route: Screen,
) {
    data object Home : BottomNavigationItem(
        title = "Dashboard",
        selectedIcon = R.drawable.home_selected,
        unselectedIcon = R.drawable.home_unselected,
        route = Screen.App.Dashboard
    )

    data object Journal : BottomNavigationItem(
        title = "Jurnal",
        selectedIcon = R.drawable.activities_selected,
        unselectedIcon = R.drawable.activities_unselected,
        route = Screen.App.MainJournalScreen
    )

    data object Profile : BottomNavigationItem(
        title = "Profil",
        selectedIcon = R.drawable.profile_selected,
        unselectedIcon = R.drawable.profile_unselected,
        route = Screen.App.Profile
    )

}