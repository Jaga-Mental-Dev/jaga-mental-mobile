package io.mindset.jagamental.ui.screen.journal.result

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mindset.jagamental.data.domain.JournalRepository
import io.mindset.jagamental.data.model.response.JournalData
import io.mindset.jagamental.utils.SharedPreferencesHelper
import io.mindset.jagamental.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JournalResultViewModel(
    private val journalRepository: JournalRepository,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    private val _journalState = MutableStateFlow<UiState<JournalData?>>(UiState.Idle)
    val journalState = _journalState.asStateFlow()


    fun getJournalById(journalId: String) {
        _journalState.value = UiState.Loading
        viewModelScope.launch {
            journalRepository.getJournalById(journalId).collect { response ->
                Log.i("InputJournalViewModel", "Response GetById: $response")
                _journalState.value = response
            }
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {

        }
    }

    fun saveJournalStatus(journalId: String, status: Boolean) {
        sharedPreferencesHelper.saveLatestCreatedJournalId(journalId)
        sharedPreferencesHelper.saveIsCompletedLatestJournalCreation(status)
    }
}