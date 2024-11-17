package io.mindset.jagamental.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import io.mindset.jagamental.R
import io.mindset.jagamental.navigation.Screen
import io.mindset.jagamental.ui.theme.tertiaryContainerLightHighContrast
import io.mindset.jagamental.utils.StatusBarColorHelper
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(navController: NavController, paddingValues: PaddingValues) {

    val viewModel: ProfileViewModel = koinViewModel()

    StatusBarColorHelper(Color.White, useDarkIcon = true)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(color = Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(
                containerColor = Color.LightGray.copy(alpha = 0.3f),
            )
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.LightGray, CircleShape)
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize(),
                            model = "https://i.pravatar.cc/300?img=53",
                            contentDescription = "Profile Image",
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = R.drawable.solar__face_scan_circle_bold),
                        )
                    }
                    Row(
                        modifier = Modifier.padding(start = 16.dp),
                    ) {
                        HeaderItem(
                            modifier = Modifier.weight(1f),
                            title = "12",
                            value = "Journal"
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        HeaderItem(
                            modifier = Modifier.weight(1f),
                            title = "Verified",
                            value = "Status"
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray.copy(alpha = 0.3f))
        ) {
            Text(
                text = "Account",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.LightGray
            )
            ProfileListItem(
                modifier = Modifier.padding(4.dp),
                label = "Edit Profile"
            )
            ProfileListItem(
                modifier = Modifier.padding(4.dp),
                label = "Change Password"
            )

            Text(
                text = "General",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.LightGray
            )
            ProfileListItem(
                modifier = Modifier.padding(4.dp),
                label = "Rate App"
            )
            ProfileListItem(
                modifier = Modifier.padding(4.dp),
                label = "Feedback"
            )
            ProfileListItem(
                modifier = Modifier.padding(4.dp),
                label = "Privacy Policy"
            )
            ProfileListItem(
                modifier = Modifier.padding(4.dp),
                label = "Terms of Service"
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray.copy(alpha = 0.3f))
                .clickable(onClick = {
                    viewModel.logout()
                    navController.navigate(Screen.Auth) {
                        popUpTo(Screen.Auth.Login) {
                            inclusive = true
                        }
                    }
                }),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Logout",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )

            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = "Profile",
                tint = Color.Red,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(20.dp)
            )
        }
    }
}

@Composable
private fun HeaderItem(
    modifier: Modifier,
    title: String,
    value: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier,
            text = value,
            fontSize = 12.sp
        )
    }
}

@Composable
fun ProfileListItem(
    modifier: Modifier = Modifier,
    label: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            )

            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = "Profile",
                tint = tertiaryContainerLightHighContrast,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}