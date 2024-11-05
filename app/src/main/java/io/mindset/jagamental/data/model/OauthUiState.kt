package io.mindset.jagamental.data.model

import com.google.firebase.auth.FirebaseUser

sealed class OauthUiState {
    object Idle : OauthUiState()
    data class Success(val user: FirebaseUser?) : OauthUiState()
    data class Error(val message: String) : OauthUiState()
}