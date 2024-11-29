package io.mindset.jagamental.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.view.View
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.view.WindowCompat

@Composable
fun StatusBarColorHelper(
    color: Color = Color.White,
    useDarkIcon: Boolean = true,
    view: View = LocalView.current,
    navBarColor: Color = Color.Transparent
) {
    val window = (view.context as Activity).window
    window.statusBarColor = color.toArgb()
    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = useDarkIcon
    window.navigationBarColor = navBarColor.toArgb()
}

@Composable
fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }
    var isScrollingUp by remember { mutableStateOf(true) }

    isScrollingUp = remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex < firstVisibleItemIndex
            } else {
                previousScrollOffset <= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value

    return isScrollingUp
}

fun getBackGroundColorByEmotion(emotion: String): Color {
    return when (emotion) {
        "senang" -> Color(0xFFFFF9C4)
        "sedih" -> Color(0xFFBBDEFB)
        "marah" -> Color(0xFFFFCDD2)
        "surprised" -> Color(0xFFFFF176)
        "disgusted" -> Color(0xFFDCEDC8)
        "fearful" -> Color(0xFFFFE082)
        "netral" -> Color(0xFFEEEEEE)
        else -> Color(0xFFFAFAFA)
    }
}

@Composable
fun LockScreenOrientation() {
    val context = LocalContext.current
    DisposableEffect(context) {
        context.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
        onDispose {
            context.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }
}

private fun Context.requireActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("No activity was present but it is required.")
}

fun PaddingValues.exceptTop(): PaddingValues {
    return PaddingValues(
        start = calculateStartPadding(LayoutDirection.Ltr),
        end = calculateEndPadding(LayoutDirection.Ltr),
        bottom = calculateBottomPadding()
    )
}
