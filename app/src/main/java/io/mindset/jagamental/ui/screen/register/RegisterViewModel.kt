package io.mindset.jagamental.ui.screen.register

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mindset.jagamental.data.domain.AuthRepository
import io.mindset.jagamental.data.model.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {

    val uiState: StateFlow<AuthState> = authRepository.uiState

    fun oauthIntent(): Intent = authRepository.signInIntent()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun registerWithEmailPassword(name: String, email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            authRepository.registerWithEmailPassword(name, email, password)
        }
    }

    fun handleOauthResult(result: ActivityResult) {
        viewModelScope.launch {
            _isLoading.value = true
            authRepository.handleSignInResult(result)
        }
    }
}