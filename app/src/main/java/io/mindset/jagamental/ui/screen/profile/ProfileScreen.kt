package io.mindset.jagamental.ui.screen.profile

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import io.mindset.jagamental.utils.LockScreenOrientation
import io.mindset.jagamental.utils.StatusBarColorHelper
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(navController: NavController, paddingValues: PaddingValues) {

    val viewModel: ProfileViewModel = koinViewModel()

    StatusBarColorHelper(Color.Transparent, useDarkIcon = false)
    LockScreenOrientation()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(RectangleShape)
                .background(color = Color(0xFF194A47))
                .padding(top = 40.dp)
                .height(130.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.people_nearby),
                contentDescription = "Mindset Logo",
                modifier = Modifier
                    .padding(end = 16.dp, top = 32.dp)
                    .align(Alignment.CenterEnd)
                    .scale(3f)
                    .height(600.dp),
                contentScale = ContentScale.Inside
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
                ),
                shape = RectangleShape
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
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

                    Text(
                        text = "John Doe",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
                            .padding(top = 16.dp)
                    )

                    Text(
                        text = "johndoe@mail.com",
                        style = TextStyle(
                            color = Color.LightGray,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
                    )
                }
            }
        }

        Column(
            modifier = Modifier
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
                color = Color.LightGray,
                thickness = 0.5.dp
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
                color = Color.LightGray,
                thickness = 0.5.dp
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

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Color.LightGray,
            thickness = 0.5.dp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable(onClick = {
                    viewModel.logout()
                    navController.navigate(Screen.Auth) {
                        popUpTo(Screen.Auth.Login) {
                            inclusive = true
                        }
                    }
                })
                .height(42.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Logout",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )

            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = "Profile",
                tint = Color.Red,
                modifier = Modifier
                    .size(20.dp)
            )
        }
    }
}

@Composable
private fun HeaderItem(
    modifier: Modifier,
    title: String,
    value: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier,
            text = value,
            fontSize = 12.sp,
            color = Color.LightGray
        )
    }
}

@Composable
fun ProfileListItem(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = { onClick() },
            )
            .clip(RoundedCornerShape(16.dp))
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