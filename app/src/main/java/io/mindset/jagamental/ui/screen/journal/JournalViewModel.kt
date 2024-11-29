package io.mindset.jagamental.ui.screen.journal

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mindset.jagamental.data.domain.JournalRepository
import io.mindset.jagamental.data.model.response.JournalData
import io.mindset.jagamental.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class JournalViewModel(
    private val journalRepository: JournalRepository
) : ViewModel() {
    private val _journalState = MutableStateFlow<UiState<List<JournalData?>?>>(UiState.Idle)
    val journalState = _journalState.asStateFlow()

    init {
        var currentDate = LocalDate.now()
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        getJournalsByDate(currentDate.format(dateFormatter))
    }

    fun getJournalsByDate(date: String) {
        viewModelScope.launch {
            _journalState.value = UiState.Loading
            journalRepository.getJournalByDate(date).collect { response ->
                Log.d("JournalViewModel", "getJournalsByDate: $response")
                _journalState.value = response
            }
        }
    }

    fun resetState() {
        _journalState.value = UiState.Loading
    }
}