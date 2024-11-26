package io.mindset.jagamental.ui.screen.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import io.mindset.jagamental.R
import io.mindset.jagamental.data.domain.AuthRepository
import io.mindset.jagamental.data.model.MenuItemProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    val uiState = authRepository.uiState
    val auth = FirebaseAuth.getInstance()
    private val _currentUser = MutableStateFlow(auth.currentUser)
    val currentUser = _currentUser.asStateFlow()

    val firstMenuItems = listOf(
        MenuItemProfile(
            icon = R.drawable.ic_phone_calling_bold_duotone,
            title = "Contact Us",
            onClick = { }
        ),
        MenuItemProfile(
            icon = R.drawable.ic_solar_star_circle_bold_duotone,
            title = "Rate Jaga Mental App",
            onClick = { }
        ),
        MenuItemProfile(
            icon = R.drawable.chatbot_unselected,
            title = "Submit Your Feedback",
            onClick = { }
        ),
        MenuItemProfile(
            icon = R.drawable.ic_solar_question_circle_bold_duotone,
            title = "FAQ",
            onClick = { }
        ),
    )

    val secondMenuItems = listOf(
        MenuItemProfile(
            icon = R.drawable.ic_mdi_instagram,
            title = "Follow Us on Instagram",
            onClick = { }
        ),
        MenuItemProfile(
            icon = R.drawable.ic_ri_twitter_x_line,
            title = "Follow Us on X",
            onClick = { }
        ),
        MenuItemProfile(
            icon = R.drawable.ic_ic_baseline_facebook,
            title = "Follow Us on Facebook",
            onClick = { }
        )
    )

    fun logout() {
        authRepository.resetToken()
        authRepository.signOut()
    }
}