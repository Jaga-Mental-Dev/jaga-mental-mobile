package io.mindset.jagamental.ui.screen.journal.add.photoresult

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mindset.jagamental.data.domain.JournalRepository
import io.mindset.jagamental.utils.SharedPreferencesHelper
import kotlinx.coroutines.launch

class PhotoResultViewModel(
    private val journalRepository: JournalRepository,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    fun deleteJournal(journalId: String) {
        viewModelScope.launch {
            Log.i("PhotoResultViewModel", "Deleting journal with ID: $journalId")
            journalRepository.deleteJournal(journalId).collect { response ->
                Log.i("DeleteJournal", "DeleteJournalResponse: $response")
            }
            Log.i("PhotoResultViewModel", "Journal deleted")
        }
    }

    fun saveJournalStatus(journalId: String, status: Boolean) {
        sharedPreferencesHelper.saveLatestCreatedJournalId(journalId)
        sharedPreferencesHelper.saveIsCompletedLatestJournalCreation(status)
    }
}