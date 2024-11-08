package io.mindset.jagamental.ui.screen.login

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mindset.jagamental.data.domain.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    val uiState = authRepository.uiState

    fun signInIntent(): Intent = authRepository.signInIntent()

    fun handleSignInResult(result: ActivityResult) {
        viewModelScope.launch {
            authRepository.handleSignInResult(result)
        }
    }

    fun signInWithEmailPassword(email: String, password: String) {
        viewModelScope.launch {
            authRepository.signInWithEmailPassword(email, password)
        }
    }
}