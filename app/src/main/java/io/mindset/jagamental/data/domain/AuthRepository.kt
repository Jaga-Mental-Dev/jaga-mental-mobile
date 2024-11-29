@file:Suppress("DEPRECATION")

package io.mindset.jagamental.data.domain

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.userProfileChangeRequest
import io.mindset.jagamental.R
import io.mindset.jagamental.utils.SharedPreferencesHelper
import io.mindset.jagamental.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthRepository(
    context: Context,
    private val preference: SharedPreferencesHelper
) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val googleSignInClient: GoogleSignInClient

    private val _uiState = MutableStateFlow<UiState<FirebaseUser?>>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    fun signInIntent(): Intent = googleSignInClient.signInIntent

    fun handleSignInResult(result: ActivityResult): Flow<UiState<Any?>> = flow {
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            emitAll(
                authenticateWithFirebase(
                    GoogleAuthProvider.getCredential(
                        account.idToken,
                        null
                    )
                )
            )
        } catch (e: ApiException) {
            Log.d("AuthRepository", "handleSignInResult: ${e.message}")
            emit(UiState.Error("Google sign in failed"))
        }
    }

    private fun authenticateWithFirebase(credential: AuthCredential): Flow<UiState<Any?>> = flow {
        try {
            auth.signInWithCredential(credential).await()
            handleUserAuthentication()
            emit(UiState.Success(auth.currentUser))
        } catch (e: Exception) {
            Log.d("AuthRepository", "authenticateWithFirebase: ${e.message}")
            emit(UiState.Error("Invalid Email or Password"))
        }
    }

    fun signInWithEmailPassword(email: String, password: String): Flow<UiState<Any?>> = flow {
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            handleUserAuthentication()
            emit(UiState.Success(auth.currentUser))
        } catch (e: FirebaseAuthException) {
            Log.e("AuthRepository", "signInWithEmailPassword: ${e.message}", e)
            emit(UiState.Error("Invalid Email or Password"))
        } catch (e: Exception) {
            Log.e("AuthRepository", "Unexpected error: ${e.message}", e)
            emit(UiState.Error("An unexpected error occurred"))
        }
    }

    fun registerWithEmailPassword(
        name: String,
        email: String,
        password: String
    ): Flow<UiState<FirebaseUser?>> = flow {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val user = auth.currentUser
            user?.let {
                it.updateProfile(userProfileChangeRequest {
                    displayName = name
                }).await()
                handleUserAuthentication()
                emit(UiState.Success(it))
            } ?: run {
                emit(UiState.Error("User registration failed"))
            }
        } catch (e: Exception) {
            Log.d("AuthRepository", "registerWithEmailPassword: ${e.message}")
            emit(UiState.Error("Registration failed: ${e.message}"))
        }
    }

    private suspend fun handleUserAuthentication() {
        val user = auth.currentUser
        if (user != null) {
            val token = getIdTokenFromFirebase()
            token?.let {
                preference.saveToken(it)
                Log.d("AuthRepository", "Token saved: $it")
            }
        }
        _uiState.value = UiState.Success(user)
    }

    suspend fun getIdTokenFromFirebase(): String? {
        return try {
            auth.currentUser?.getIdToken(true)?.await()?.token
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error getting token: ${e.message}")
            null
        }
    }

    fun signOut() {
        auth.signOut()
        googleSignInClient.signOut()
        preference.clearToken()
        _uiState.value = UiState.Idle
    }

    fun resetToken() {
        preference.clearToken()
    }
}
