package io.mindset.jagamental.data.model

import com.google.firebase.auth.FirebaseUser

sealed class AuthState {
    object Idle : AuthState()
    data class Success(val user: FirebaseUser?) : AuthState()
    data class Error(val message: String) : AuthState()
}