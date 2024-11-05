package io.mindset.jagamental.ui.screen.login

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.mindset.jagamental.R
import io.mindset.jagamental.data.model.OauthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(context: Context) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val googleSignInClient: GoogleSignInClient

    private val _uiState = MutableStateFlow<OauthUiState>(OauthUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _idToken = MutableStateFlow<String>("")
    val idToken = _idToken.asStateFlow()

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    fun signInIntent(): Intent = googleSignInClient.signInIntent

    fun handleSignInResult(result: ActivityResult) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!

            _idToken.value = account.idToken ?: ""

            viewModelScope.launch {
                firebaseAuthWithGoogle(account.idToken!!)
            }
        } catch (e: ApiException) {
            _uiState.value = OauthUiState.Error("Google sign in failed: $e")
        }
    }

    private suspend fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _uiState.value = OauthUiState.Success(auth.currentUser)
                sendIdTokenToApi(_idToken.value)
            } else {
                _uiState.value =
                    OauthUiState.Error("Authentication failed: ${task.exception?.message}")
            }
        }
    }

    fun signInWithEmailPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _uiState.value = OauthUiState.Success(auth.currentUser)
            } else {
                _uiState.value =
                    OauthUiState.Error("Authentication failed: ${task.exception?.message}")
            }
        }
    }

    private fun sendIdTokenToApi(idToken: String) {
        Log.d("OAuth", "Sending ID Token to API: $idToken")
//        TODO("Not yet implemented")
    }
}