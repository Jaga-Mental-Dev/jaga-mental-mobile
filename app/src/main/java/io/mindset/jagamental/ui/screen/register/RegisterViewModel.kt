package io.mindset.jagamental.ui.screen.register

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import io.mindset.jagamental.data.domain.AuthRepository
import io.mindset.jagamental.data.domain.LoginRepository
import io.mindset.jagamental.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _registerState = MutableStateFlow<UiState<FirebaseUser?>>(UiState.Idle)
    val registerState = _registerState.asStateFlow()

    fun oauthIntent(): Intent = authRepository.signInIntent()


    fun registerWithEmailPassword(name: String, email: String, password: String) {
        viewModelScope.launch {
            _registerState.value = UiState.Loading
            authRepository.registerWithEmailPassword(name, email, password).collect { authState ->
                when (authState) {
                    is UiState.Success -> {
                        authRepository.signOut()
                        _registerState.value = UiState.Success(authState.data)
                    }

                    is UiState.Error -> {
                        _registerState.value = UiState.Error(authState.message)
                        Log.d("LoginViewModel", "signInWithEmailPassword: ${authState.message}")
                    }

                    else -> {
                        Log.d("LoginViewModel", "signInWithEmailPassword: $authState")
                    }
                }
            }
        }
    }

    fun handleOauthResult(result: ActivityResult) {
        viewModelScope.launch {
            authRepository.handleSignInResult(result).collect { authState ->
                when (authState) {
                    is UiState.Success -> {
                        authRepository.signOut()
                        UiState.Success(Unit)
                    }

                    is UiState.Error -> {
                        _registerState.value = UiState.Error(authState.message)
                        Log.d("RegisterViewModel", "Error: ${authState.message}")
                    }

                    else -> {
                        Log.d("RegisterViewModel", "Unknown: $authState")
                    }
                }
            }
        }
    }

    fun resetState() {
        _registerState.value = UiState.Idle
    }
}