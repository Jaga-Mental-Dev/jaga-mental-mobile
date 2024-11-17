package io.mindset.jagamental.ui.screen.profile

import androidx.lifecycle.ViewModel
import io.mindset.jagamental.data.domain.AuthRepository

class ProfileViewModel(private val authRepository: AuthRepository): ViewModel() {
    val uiState = authRepository.uiState

    fun logout() {
        authRepository.signOut()
    }
}