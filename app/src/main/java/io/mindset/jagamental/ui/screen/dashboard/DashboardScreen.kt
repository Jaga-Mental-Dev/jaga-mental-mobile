package io.mindset.jagamental.ui.screen.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import io.mindset.jagamental.R
import io.mindset.jagamental.ui.component.chart.DashLineChart
import io.mindset.jagamental.ui.component.dashboard.DashboardHeader
import io.mindset.jagamental.ui.component.dashboard.EmotionCard
import io.mindset.jagamental.utils.StatusBarColorHelper
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(
    navController: NavController,
    paddingValues: PaddingValues,
) {
    val viewModel: DashboardViewModel = koinViewModel()
    val modifiedPaddingValues =
        PaddingValues(
            start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
            top = 0.dp,
            end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
            bottom = paddingValues.calculateBottomPadding(),
        )

    val graphData = viewModel.graphData.collectAsState()
    val currentUser = remember { mutableStateOf(viewModel.user.value) }
    val userName = currentUser.value?.displayName ?: ""

    LaunchedEffect(Unit) {
        viewModel.getGraphData()
    }

    StatusBarColorHelper(Color.Transparent, useDarkIcon = false)
    Column(
        modifier =
        Modifier
            .padding(modifiedPaddingValues)
            .padding(top = 0.dp)
            .fillMaxSize()
            .background(color = Color.White),
    ) {
        DashboardHeader(userName)

        Column(
            modifier =
                Modifier
                    .verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = stringResource(R.string.dashboard),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(16.dp),
            )

            Row(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                Box(
                    modifier =
                    Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .height(140.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color(0xFFE8EDED), shape = RoundedCornerShape(8.dp)),
                ) {
                    Column(
                        modifier =
                        Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_solar_document),
                            contentDescription = stringResource(R.string.account_circle),
                            modifier =
                            Modifier
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
                    modifier =
                    Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .height(140.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color(0xFFE8EDED), shape = RoundedCornerShape(8.dp)),
                ) {
                    Column(
                        modifier =
                        Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_solar_document),
                            contentDescription = stringResource(R.string.account_circle),
                            modifier =
                            Modifier
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
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(73.dp)
                    .clip(RectangleShape),
            )

            Text(
                text = stringResource(R.string.mindful_tracker),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier =
                Modifier
                    .padding(16.dp)
                    .padding(top = 20.dp),
            )

            DashLineChart(
                graphData = graphData.value,
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(200.dp),
            )
        }
    }
}