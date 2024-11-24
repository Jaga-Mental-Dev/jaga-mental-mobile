package io.mindset.jagamental.ui.screen.login

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mindset.jagamental.data.domain.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    val uiState = authRepository.uiState

    fun signInIntent(): Intent = authRepository.signInIntent()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    fun handleSignInResult(result: ActivityResult) {
        viewModelScope.launch {
            _isLoading.value = true
            authRepository.handleSignInResult(result)
        }
    }

    fun signInWithEmailPassword(email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            authRepository.signInWithEmailPassword(email, password)
        }
    }
}