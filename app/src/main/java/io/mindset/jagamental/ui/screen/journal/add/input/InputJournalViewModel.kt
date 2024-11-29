package io.mindset.jagamental.ui.screen.journal.add.input

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mindset.jagamental.data.domain.JournalRepository
import io.mindset.jagamental.data.model.request.JournalRequest
import io.mindset.jagamental.data.model.response.JournalData
import io.mindset.jagamental.utils.SharedPreferencesHelper
import io.mindset.jagamental.utils.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InputJournalViewModel(
    private val journalRepository: JournalRepository,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    private val _journalState = MutableStateFlow<UiState<JournalData?>>(UiState.Idle)
    val journalState = _journalState.asStateFlow()

    private val _timerState = MutableStateFlow(0) // Timer in seconds
    val timerState = _timerState.asStateFlow()

    private var timerJob: Job? = null

    fun startTimer() {
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000) // Delay for 1 second
                _timerState.value++
            }
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
    }

    fun updateJournal(
        journalId: String,
        title: String,
        content: String
    ) {
        _journalState.value = UiState.Loading
        viewModelScope.launch {
            val journalData: JournalRequest = JournalRequest(
                title = title,
                content = content
            )
            journalRepository.putUpdateJournal(journalId, journalData).collect { response ->
                Log.i("InputJournalViewModel", "Response Update: $response")
                if (response is UiState.Error) {
                    saveJournalStatus(journalId, false)
                }
                _journalState.value = response
            }
        }
    }

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

    fun resetState() {
        _journalState.value = UiState.Idle
    }
}