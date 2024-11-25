package io.mindset.jagamental.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.mindset.jagamental.R
import io.mindset.jagamental.navigation.Screen
import io.mindset.jagamental.ui.component.profile.LogoutConfirmationDialog
import io.mindset.jagamental.ui.component.profile.ProfileHeader
import io.mindset.jagamental.utils.LockScreenOrientation
import io.mindset.jagamental.utils.StatusBarColorHelper
import io.mindset.jagamental.utils.exceptTop
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, paddingValues: PaddingValues) {
    val viewModel: ProfileViewModel = koinViewModel()
    val user = remember { mutableStateOf(viewModel.currentUser.value) }
    val firstMenus = viewModel.firstMenuItems
    val secondMenus = viewModel.secondMenuItems

    val sheetState = rememberModalBottomSheetState()
    var showDialog by remember { mutableStateOf(false) }

    StatusBarColorHelper(Color.Transparent, useDarkIcon = false)
    LockScreenOrientation()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(paddingValues.exceptTop()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileHeader(
            userName = user.value?.displayName,
            userEmail = user.value?.email,
            userPhotoUrl = user.value?.photoUrl?.toString()
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 10.dp)
            ) {
                firstMenus.forEach { item ->
                    ProfileMenuItem(
                        icon = item.icon,
                        label = item.title,
                        onClick = item.onClick
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 20.dp)
            ) {
                secondMenus.forEach { item ->
                    ProfileMenuItem(
                        icon = item.icon,
                        label = item.title,
                        onClick = item.onClick
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 20.dp)
            ) {
                ProfileMenuItem(
                    icon = R.drawable.ic_solar_login_2_bold_duotone,
                    label = stringResource(id = R.string.logout),
                    onClick = {
                        showDialog = true
                    },
                    labelColor = Color.Red
                )
            }
        }
    }

    if (showDialog) {
        LogoutConfirmationDialog(
            sheetState = sheetState,
            onConfirm = {
                viewModel.logout()
                navController.navigate(Screen.Auth.Login) {
                    popUpTo(Screen.App.Profile) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
                showDialog = false
            },
            onDismis = {
                showDialog = false
            }
        )
    }
}

@Composable
fun ProfileMenuItem(
    icon: Int,
    label: String,
    onClick: () -> Unit = {},
    labelColor: Color? = null
) {
    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(icon),
                    tint = Color.DarkGray,
                    contentDescription = label,
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = label,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = labelColor ?: Color.Black,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = "Profile",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        navController = NavController(
            context = LocalContext.current
        ),
        paddingValues = PaddingValues(0.dp)
    )
}