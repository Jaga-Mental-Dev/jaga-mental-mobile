package io.mindset.jagamental.ui.screen.login

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mindset.jagamental.data.domain.AuthRepository
import io.mindset.jagamental.data.domain.LoginRepository
import io.mindset.jagamental.data.model.response.AuthResponse
import io.mindset.jagamental.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val loginRepository: LoginRepository
) : ViewModel() {

    fun oauthIntent(): Intent = authRepository.signInIntent()

    private val _loginState = MutableStateFlow<UiState<AuthResponse>>(UiState.Idle)
    val loginState = _loginState.asStateFlow()

    fun handleOauthInResult(result: ActivityResult) {
        viewModelScope.launch {
            authRepository.handleSignInResult(result).collect { authState ->
                when (authState) {
                    is UiState.Success -> {
                        apiLogin()
                    }

                    is UiState.Error -> {
                        _loginState.value = UiState.Error(authState.message)
                        Log.d("LoginViewModel", "handleSignInResult: ${authState.message}")
                    }

                    else -> {
                        Log.d("LoginViewModel", "handleSignInResult: $authState")
                    }
                }
            }
        }
    }

    fun signInWithEmailPassword(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            authRepository.signInWithEmailPassword(email, password).collect { authState ->
                when (authState) {
                    is UiState.Success -> {
                        apiLogin()
                    }

                    is UiState.Error -> {
                        _loginState.value = UiState.Error(authState.message)
                        Log.d("LoginViewModel", "signInWithEmailPassword: ${authState.message}")
                    }

                    else -> {
                        Log.d("LoginViewModel", "signInWithEmailPassword: $authState")
                    }
                }
            }
        }
    }

    private fun apiLogin() {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            loginRepository.postAuth().collect { result ->
                Log.d("LoginViewModel", "apiLogin: $result")
                _loginState.value = when (result) {
                    is UiState.Success -> UiState.Success(result.data)
                    is UiState.Error -> {
                        authRepository.signOut()
                        UiState.Error("Login failed")
                    }

                    is UiState.Loading -> {
                        UiState.Loading
                    }

                    else -> UiState.Idle
                }
            }
        }
    }

    fun resetState() {
        _loginState.value = UiState.Idle
    }
}