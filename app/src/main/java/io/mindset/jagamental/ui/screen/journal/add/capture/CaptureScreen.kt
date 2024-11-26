package io.mindset.jagamental.ui.screen.journal.add.capture

import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import io.mindset.jagamental.R
import io.mindset.jagamental.navigation.Screen
import io.mindset.jagamental.ui.component.camera.CameraContent
import io.mindset.jagamental.ui.component.camera.CameraLoadingLottie
import io.mindset.jagamental.ui.component.journal.OverlayView
import io.mindset.jagamental.ui.screen.others.nopermission.NoPermissionScreen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CaptureScreen(navController: NavController) {
    val viewModel: CaptureViewModel = koinViewModel()
    val context = LocalContext.current

    val cameraPermissionState: PermissionState =
        rememberPermissionState(android.Manifest.permission.CAMERA)
    val onRequestPermission = { cameraPermissionState.launchPermissionRequest() }

    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val cameraController: LifecycleCameraController =
        remember { LifecycleCameraController(context) }

    val onPhotoCaptured: (String) -> Unit = { photoUrl ->
        navController.navigate(Screen.App.ResultPreviewScreen(photoUrl)) {
            launchSingleTop = true
        }
    }

    val isLoading = viewModel.isLoading.collectAsState()

    if (cameraPermissionState.status.isGranted) {
        Box {
            CameraContent(
                cameraController = cameraController,
                lifecycleOwner = lifecycleOwner
            )

            OverlayView(
                modifier = Modifier.fillMaxSize(),
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(25))
                        .size(33.dp),
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }

                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(33.dp)
                        .background(Color.White, shape = RoundedCornerShape(25)),
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .align(Alignment.Center),
                        text = stringResource(R.string.input_step_1_3),
                        color = Color.Black,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 16.sp
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 12.dp)
                        .background(Color.White.copy(0.5f), RoundedCornerShape(40))
                        .border(2.dp, Color.Gray.copy(0.3f), RoundedCornerShape(40))
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        text = stringResource(R.string.place_your_face_inside_the_shape),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )

                }
                FloatingActionButton(
                    modifier = Modifier
                        .border(2.dp, Color.LightGray.copy(0.3f), RoundedCornerShape(30)),
                    onClick = {
                        viewModel.capturePhoto(
                            context,
                            cameraController,
                            onPhotoCaptured
                        )
                    },
                    containerColor = Color(0xFF194A47),
                    shape = RoundedCornerShape(30),
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = stringResource(R.string.camera_capture_icon)
                    )
                }
            }

            if (isLoading.value) {
                CameraLoadingLottie()
            }
        }
    } else {
        NoPermissionScreen(
            onRequestPermission = onRequestPermission
        )
    }
}