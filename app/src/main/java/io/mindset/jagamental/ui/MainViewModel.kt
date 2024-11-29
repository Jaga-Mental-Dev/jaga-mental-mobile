package io.mindset.jagamental.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mindset.jagamental.data.domain.JournalRepository
import io.mindset.jagamental.utils.SharedPreferencesHelper
import kotlinx.coroutines.launch

class MainViewModel(
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val journalRepository: JournalRepository
) : ViewModel() {

    fun checkLatestJournalStatus() {
        val latestJournalId: String = sharedPreferencesHelper.getLatestCreatedJournalId().toString()
        val isComplete: Boolean = sharedPreferencesHelper.isCompletedLatestJournalCreation()

        if (!isComplete) {
            viewModelScope.launch {
                journalRepository.deleteJournal(latestJournalId).collect { response ->
                    Log.i("MainViewModel", "Latest Incomplete Journal Deleted")
                }
            }
        }
    }
}