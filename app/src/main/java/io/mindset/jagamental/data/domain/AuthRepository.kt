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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.mindset.jagamental.R
import io.mindset.jagamental.data.model.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

class AuthRepository(context: Context) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val googleSignInClient: GoogleSignInClient

    private val _uiState = MutableStateFlow<AuthState>(AuthState.Idle)
    val uiState = _uiState.asStateFlow()

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
        Log.d("AuthRepository", "init: ${auth.currentUser}")
    }

    fun signInIntent(): Intent = googleSignInClient.signInIntent

    suspend fun handleSignInResult(result: ActivityResult) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            Log.d("AuthRepository", "handleSignInResult: ${e.message}")
            _uiState.value = AuthState.Error("Google sign in failed: $e")
        }
    }

    private suspend fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        try {
            auth.signInWithCredential(credential).await()
            _uiState.value = AuthState.Success(auth.currentUser)
        } catch (e: Exception) {
            Log.d("AuthRepository", "firebaseAuthWithGoogle: ${e.message}")
            _uiState.value = AuthState.Error("Authentication failed: ${e.message}")
        }
    }

    suspend fun signInWithEmailPassword(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            _uiState.value = AuthState.Success(auth.currentUser)
        } catch (e: Exception) {
            _uiState.value = AuthState.Error("Authentication failed: ${e.message}")
        }
    }

    suspend fun registerWithEmailPassword(email: String, password: String) {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            _uiState.value = AuthState.Success(auth.currentUser)
        } catch (e: Exception) {
            _uiState.value = AuthState.Error("Registration failed: ${e.message}")
        }
    }

    fun signOut() {
        auth.signOut()
        googleSignInClient.signOut()
        _uiState.value = AuthState.Idle
    }
}