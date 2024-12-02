package io.mindset.jagamental.ui.screen.dashboard

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.valentinilk.shimmer.shimmer
import io.mindset.jagamental.R
import io.mindset.jagamental.navigation.Screen
import io.mindset.jagamental.ui.component.chart.DashLineChart
import io.mindset.jagamental.ui.component.dashboard.DashboardHeader
import io.mindset.jagamental.ui.component.dashboard.EmotionCard
import io.mindset.jagamental.ui.component.dashboard.ProfesionalCard
import io.mindset.jagamental.utils.StatusBarColorHelper
import io.mindset.jagamental.utils.UiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(
    navController: NavController,
    paddingValues: PaddingValues,
) {
    val viewModel: DashboardViewModel = koinViewModel()
    val modifiedPaddingValues = PaddingValues(
        start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
        top = 0.dp,
        end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
        bottom = paddingValues.calculateBottomPadding(),
    )

    val chartData = viewModel.graphData.collectAsState()
    val currentUser = remember { mutableStateOf(viewModel.user.value) }
    currentUser.value?.getIdToken(true)?.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Log.i("DashboardScreen", "User Token: ${task.result.token}")
        }
    }
    val userName = currentUser.value?.displayName ?: ""

    val showDialog = remember { mutableStateOf(false) }
    val dialogText = remember { mutableStateOf("") }
    val dialogTitle = remember { mutableStateOf("") }
    val profesionals = viewModel.profesionals.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getGraphData()
    }

    StatusBarColorHelper(Color.Transparent, useDarkIcon = false)
    Column(
        modifier = Modifier
            .padding(modifiedPaddingValues)
            .padding(top = 0.dp)
            .fillMaxSize()
            .background(color = Color.White),
    ) {

        if (showDialog.value) {
            CustomDialog(
                onDismiss = { showDialog.value = false },
                title = dialogTitle.value,
                message = dialogText.value
            )
        }

        DashboardHeader(userName)

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(vertical = 12.dp),
        ) {
            Text(
                text = stringResource(R.string.dashboard),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(16.dp),
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .height(140.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color(0xFFE8EDED), shape = RoundedCornerShape(8.dp))
                        .clickable(onClick = {
                            showDialog.value = true
                            dialogTitle.value = "Coming Soon"
                            dialogText.value = "Sabar ya, fitur ini masih dalam pengembangan."
                        }),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_solar_document),
                            contentDescription = stringResource(R.string.account_circle),
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color(0xFFD1DBDA), shape = RoundedCornerShape(8.dp))
                                .padding(4.dp),
                        )

                        Text(
                            text = stringResource(R.string.chatbot),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .height(140.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color(0xFFE8EDED), shape = RoundedCornerShape(8.dp))
                        .clickable(
                            onClick = {
                                navController.navigate(Screen.App.MainJournalScreen) {
                                    launchSingleTop = true
                                }
                            }
                        ),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_solar_document),
                            contentDescription = stringResource(R.string.account_circle),
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color(0xFFD1DBDA), shape = RoundedCornerShape(8.dp))
                                .padding(4.dp),
                        )

                        Text(
                            text = stringResource(R.string.journal),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }

            Text(
                text = stringResource(R.string.how_are_you_feeling),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(16.dp),
            )

            EmotionCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(73.dp)
                    .clip(RectangleShape)
                    .clickable(
                        onClick = {
                            navController.navigate(Screen.App.CapturePhotoScreen) {
                                launchSingleTop = true
                            }
                        }
                    ),
            )

            Text(
                text = stringResource(R.string.mindful_tracker),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(top = 20.dp),
            )

            when (val chartState = chartData.value) {
                is UiState.Success -> {
                    DashLineChart(
                        graphData = chartState.data,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 12.dp)
                            .height(200.dp),
                    )
                }

                is UiState.Error -> {
                    ChartError(
                        onRetry = { viewModel.getGraphData() }
                    )
                }

                is UiState.Idle -> {}
                is UiState.Loading -> {
                    ChartLoading()
                }
            }

            Text(
                text = "Profesional Terbaik",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(top = 20.dp),
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                itemsIndexed(profesionals.value) { index, professional ->
                    ProfesionalCard(
                        data = professional,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialog(
    onDismiss: () -> Unit,
    title: String,
    message: String,
) {
    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(title)
        },
        icon = {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Error Icon"
            )
        },
        text = {
            Text(message)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Ok")
            }
        }
    )
}

@Composable
fun ChartError(
    onRetry: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Rounded.Warning,
                contentDescription = null
            )
            Text("Terjadi Kesalahan, silahkan coba lagi.")
            Button(
                onClick = { onRetry() }
            ) {
                Text("Coba Lagi")
            }
        }
    }
}

@Composable
fun ChartLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(color = Color.Transparent)
            .padding(16.dp)
            .shimmer(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(200.dp)
                .background(color = Color.Gray, shape = RoundedCornerShape(16.dp))
        )
    }
}