package io.mindset.jagamental.data.domain

import io.mindset.jagamental.data.model.request.AnalyticRequest
import io.mindset.jagamental.data.model.response.EmotionDataItem
import io.mindset.jagamental.data.model.response.JournalDataItem
import io.mindset.jagamental.data.remote.ApiService
import io.mindset.jagamental.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody

class JournalRepository(private val apiService: ApiService) {
    fun getJournals(
        title: String? = null,
        content: String? = null,
        emotion: String? = null
    ): Flow<UiState<List<JournalDataItem>>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.doGetJournalByUserId(title, content, emotion)
            if (response.error == true) {
                emit(UiState.Error(response.message ?: "Unknown error occurred"))
            } else {
                emit(UiState.Success(response.data))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Error fetching journals"))
        }
    }

    fun getJournalById(id: String): Flow<UiState<JournalDataItem>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.doJournalById(id)
            if (response.error == true) {
                emit(UiState.Error(response.message ?: "Unknown error occurred"))
            } else {
                response.data.firstOrNull()?.let {
                    emit(UiState.Success(it))
                } ?: emit(UiState.Error("Journal not found"))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Error fetching journal details"))
        }
    }

    fun createJournal(
        title: String?,
        content: String?,
        emotion: String?,
        selfie: MultipartBody.Part
    ): Flow<UiState<JournalDataItem>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.doCreateJournal(title, content, emotion, selfie)
            if (response.error == true) {
                emit(UiState.Error(response.message ?: "Unknown error occurred"))
            } else {
                response.data.firstOrNull()?.let {
                    emit(UiState.Success(it))
                } ?: emit(UiState.Error("Failed to create journal"))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Error creating journal"))
        }
    }

    fun updateJournal(
        id: String,
        title: String?,
        content: String?,
        emotion: String?,
        selfie: MultipartBody.Part // or MultipartBody.Part if it's a file
    ): Flow<UiState<JournalDataItem>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.doUpdateJournal(id, title, content, emotion, selfie)
            if (response.error == true) {
                emit(UiState.Error(response.message ?: "Unknown error occurred"))
            } else {
                response.data.firstOrNull()?.let {
                    emit(UiState.Success(it))
                } ?: emit(UiState.Error("Failed to update journal"))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Error updating journal"))
        }
    }

    fun deleteJournal(id: String): Flow<UiState<String>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.doDeleteJournal(id)
            if (response.error == true) {
                emit(UiState.Error(response.message ?: "Unknown error occurred"))
            } else {
                emit(UiState.Success("Journal deleted successfully"))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Error deleting journal"))
        }
    }

    fun getJournalByDate(date: String): Flow<UiState<List<JournalDataItem>>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.doGetJournalByDate(date)
            if (response.error == true) {
                emit(UiState.Error(response.message ?: "Unknown error occurred"))
            } else {
                emit(UiState.Success(response.data))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Error fetching journals by date"))
        }
    }

    fun postAnalyticData(request: AnalyticRequest): Flow<UiState<List<EmotionDataItem>>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.doPostAnalytic(request)
            if (response.error == true) {
                emit(UiState.Error(response.message ?: "Unknown error occurred"))
            } else {
                response.data?.let {
                    emit(UiState.Success(it))
                } ?: emit(UiState.Error("Failed to post analytic data"))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Error posting analytic data"))
        }
    }

}