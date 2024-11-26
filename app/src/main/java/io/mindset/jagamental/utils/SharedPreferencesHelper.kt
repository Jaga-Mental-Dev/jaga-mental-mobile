package io.mindset.jagamental.utils

import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SharedPreferencesHelper(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val TOKEN_KEY = "TOKEN_KEY"
        private const val NAME_KEY = "NAME_KEY"

    }

    fun saveToken(token: String) {
        sharedPreferences.edit().remove(TOKEN_KEY).apply()
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken() {
        sharedPreferences.getString(TOKEN_KEY, "")
    }

    suspend fun getTokenAsync(): String = withContext(Dispatchers.IO) {
        val token = sharedPreferences.getString(TOKEN_KEY, "") ?: ""
        Log.d("SharedPreferencesHelper", "getToken: $token")
        token
    }

    fun clearToken() {
        sharedPreferences.edit()
            .remove(TOKEN_KEY)
            .remove(NAME_KEY)
            .apply()
    }
}