package io.mindset.jagamental.ui.screen.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.mindset.jagamental.data.domain.JournalRepository
import io.mindset.jagamental.data.model.ChartData
import io.mindset.jagamental.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val journalRepository: JournalRepository
) : ViewModel() {

    val auth = FirebaseAuth.getInstance()
    private val _graphData = MutableStateFlow<UiState<List<ChartData>>>(UiState.Loading)
    val graphData = _graphData.asStateFlow()

    private val _user = MutableStateFlow<FirebaseUser?>(null)
    val user = _user.asStateFlow()

    init {
        getUser()
        getGraphData()
    }

    fun getGraphData() {
        _graphData.value = UiState.Loading
        viewModelScope.launch {
            journalRepository.postAnalyticData().collect { response ->
                _graphData.value = response
            }
        }
    }

    fun getUser() {
        _user.value = auth.currentUser
    }

}