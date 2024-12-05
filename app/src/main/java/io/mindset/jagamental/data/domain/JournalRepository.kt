package io.mindset.jagamental.data.domain

import android.util.Log
import io.mindset.jagamental.data.model.ChartData
import io.mindset.jagamental.data.model.ProfessionalProfile
import io.mindset.jagamental.data.model.request.JournalRequest
import io.mindset.jagamental.data.model.response.JournalData
import io.mindset.jagamental.data.remote.ApiService
import io.mindset.jagamental.utils.ProState
import io.mindset.jagamental.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody


class JournalRepository(private val apiService: ApiService) {
    /*Enum implementation
    * val selectedEmotion = EmotionRequest.sad.toString()
    * */

    fun getJournals(): Flow<UiState<List<JournalData?>>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.doGetJournalByUserId()
            if (response.error == true) {
                emit(UiState.Error(response.message ?: "Unknown error occurred"))
            } else {
                response.data?.let {
                    emit(UiState.Success(it))
                } ?: emit(UiState.Error("Failed to fetch journals"))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Error fetching journals"))
        }
    }

    fun postCreateJournal(
        selfie: MultipartBody.Part
    ): Flow<UiState<JournalData>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.doCreateJournal(selfie)
            if (response.error == true) {
                emit(UiState.Error(response.message ?: "Unknown error occurred"))
            } else {
                response.data?.let {
                    emit(UiState.Success(it))
                } ?: emit(UiState.Error("Failed to create journal"))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Error creating journal"))
        }
    }

    fun putUpdateJournal(
        id: String,
        contentJournal: JournalRequest
    ): Flow<UiState<JournalData>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.doUpdateJournal(id, contentJournal)
            if (response.error == true) {
                Log.i("JournalRepository", "Response Update: $response")
                emit(UiState.Error(response.message ?: "Unknown error occurred"))
            } else {
                Log.i("JournalRepository", "Response Update: $response")
                response.data?.let {
                    emit(UiState.Success(it))
                } ?: emit(UiState.Error("Failed to update journal"))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Error updating journal"))
        }
    }

    fun getJournalById(id: String): Flow<UiState<JournalData>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.doJournalById(id)
            if (response.error == true) {
                emit(UiState.Error(response.message ?: "Unknown error occurred"))
            } else {
                response.data?.let {
                    emit(UiState.Success(it))
                } ?: emit(UiState.Error("Journal not found"))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Error fetching journal details"))
        }
    }

    fun deleteJournal(id: String): Flow<UiState<String>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.doDeleteJournal(id)
            if (response.error) {
                emit(UiState.Error(response.message ?: "Unknown error occurred"))
            } else {
                emit(UiState.Success("MainJournalScreen deleted successfully"))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Error deleting journal"))
        }
    }

    fun getJournalByDate(date: String): Flow<UiState<List<JournalData?>>> = flow {
        emit(UiState.Loading)
        try {
            val req: JournalRequest = JournalRequest(
                date = date
            )
            val response = apiService.doGetJournalByDate(req)
            if (response.error == true) {
                emit(UiState.Error(response.message ?: "Unknown error occurred"))
            } else {
                response.data?.let {
                    emit(UiState.Success(response.data))
                }
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Error fetching journals by date"))
        }
    }

    fun postAnalyticData(): Flow<UiState<List<ChartData>>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.doPostAnalytic()
            if (response.error == true) {
                emit(UiState.Error(response.message ?: "Unknown error occurred"))
            } else {
                response.data?.let {
                    emit(UiState.Success(it.filterNotNull()))
                } ?: emit(UiState.Error("Failed to get analytic data"))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Error getting analytic data"))
        }
    }

    fun getProfessionals(): Flow<ProState<List<ProfessionalProfile?>>> = flow {
        emit(ProState.Loading)
        try {
            val response = apiService.doGetProfessional()
            if (response.error == true) {
                emit(ProState.Error(response.message))
            } else {
                emit(ProState.Success(response.data))
            }
        } catch (e: Exception) {
            emit(ProState.Error(e.localizedMessage ?: "Error getting professionals"))
        }
    }
}