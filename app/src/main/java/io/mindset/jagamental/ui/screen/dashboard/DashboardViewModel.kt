package io.mindset.jagamental.ui.screen.dashboard

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.mindset.jagamental.data.domain.AuthRepository
import io.mindset.jagamental.data.model.ChartData
import io.mindset.jagamental.utils.ColorHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel(private val authRepository: AuthRepository): ViewModel() {

    val auth = FirebaseAuth.getInstance()
    private val _graphData = MutableStateFlow<List<ChartData?>>(emptyList())
    val graphData = _graphData.asStateFlow()
    val colorHelper = ColorHelper

    private val _user = MutableStateFlow<FirebaseUser?>(null)
    val user = _user.asStateFlow()

    init {
        getUser()
        getGraphData()
    }

    fun getGraphData() {
        //TODO: Get data from repository
        // dummy data
        _graphData.value = listOf(
            ChartData(
                "Sedih",
                listOf(2.0, 3.0, 4.0, 0.0, 1.0, 2.0, 3.0),
                colorHelper.getColorByLabel("Sedih")
            ),
            ChartData(
                "Senang",
                listOf(1.0, 3.0, 2.0, 4.0, 2.0, 5.0, 1.0),
                colorHelper.getColorByLabel("Senang")
            ),
            ChartData(
                "Netral",
                listOf(3.0, 1.0, 0.0, 2.0, 4.0, 0.0, 1.0),
                colorHelper.getColorByLabel("Netral")
            ),
            ChartData(
                "Marah",
                listOf(4.0, 5.0, 3.0, 5.0, 4.0, 5.0, 3.0),
                colorHelper.getColorByLabel("Marah")
            )
        )
    }

    fun getUser() {
        _user.value = auth.currentUser
    }

}