package io.mindset.jagamental.utils

import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SharedPreferencesHelper(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val TOKEN_KEY = "TOKEN_KEY"
        private const val LATEST_CREATED_JOURNAL_ID = "LATEST_CREATED_JOURNAL_ID"
        private const val IS_COMPLETED_JOURNAL_CREATION = "IS_COMPLETED_JOURNAL_CREATION"
    }

    fun saveLatestCreatedJournalId(journalId: String) {
        sharedPreferences.edit()
            .putString(LATEST_CREATED_JOURNAL_ID, journalId)
            .apply()
    }

    fun getLatestCreatedJournalId(): String? {
        return sharedPreferences.getString(LATEST_CREATED_JOURNAL_ID, null) ?: ""
    }

    fun saveIsCompletedLatestJournalCreation(isCompleted: Boolean) {
        sharedPreferences.edit()
            .putBoolean(IS_COMPLETED_JOURNAL_CREATION, isCompleted)
            .apply()
    }

    fun isCompletedLatestJournalCreation(): Boolean {
        return sharedPreferences.getBoolean(IS_COMPLETED_JOURNAL_CREATION, false)
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
            .apply()
    }
}