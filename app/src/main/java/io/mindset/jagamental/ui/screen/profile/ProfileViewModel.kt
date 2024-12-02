package io.mindset.jagamental.ui.screen.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import io.mindset.jagamental.data.domain.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    val uiState = authRepository.uiState
    val auth = FirebaseAuth.getInstance()
    private val _currentUser = MutableStateFlow(auth.currentUser)
    val currentUser = _currentUser.asStateFlow()

    fun logout() {
        authRepository.resetToken()
        authRepository.signOut()
    }
}