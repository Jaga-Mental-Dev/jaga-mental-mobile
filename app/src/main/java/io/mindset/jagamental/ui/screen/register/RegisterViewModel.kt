package io.mindset.jagamental.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mindset.jagamental.data.domain.AuthRepository
import io.mindset.jagamental.data.model.AuthState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {

    val uiState: StateFlow<AuthState> = authRepository.uiState

    fun registerWithEmailPassword(email: String, password: String) {
        viewModelScope.launch {
            authRepository.registerWithEmailPassword(email, password)
        }
    }
}