package io.mindset.jagamental.ui.screen.dashboard

import androidx.lifecycle.ViewModel
import io.mindset.jagamental.data.domain.AuthRepository
import io.mindset.jagamental.data.model.ChartData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel(private val authRepository: AuthRepository): ViewModel() {

    private val _graphData = MutableStateFlow<List<ChartData?>>(emptyList())
    val graphData = _graphData.asStateFlow()

    fun getGraphData() {
        //TODO: Get data from repository
    }
}