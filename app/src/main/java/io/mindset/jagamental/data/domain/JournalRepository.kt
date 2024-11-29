package io.mindset.jagamental.data.domain

import android.util.Log
import io.mindset.jagamental.data.model.request.AnalyticRequest
import io.mindset.jagamental.data.model.request.JournalRequest
import io.mindset.jagamental.data.model.response.DataEmotion
import io.mindset.jagamental.data.model.response.JournalData
import io.mindset.jagamental.data.remote.ApiService
import io.mindset.jagamental.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody


enum class EmotionRequest(val queryValue: String) {
    sad("sedih"),
    angry("marah"),
    neutral("netral"),
    happy("senang");

    override fun toString(): String = queryValue
}

class JournalRepository(private val apiService: ApiService) {
    /*Enum implementation
    * val selectedEmotion = EmotionRequest.sad.toString()
    * */

//    fun getJournals(
//        title: String? = null,
//        content: String? = null,
//        emotion: String? = null
//    ): Flow<UiState<List<JournalData>?>> = flow {
//        emit(UiState.Loading)
//        try {
//            val response = apiService.doGetJournalByUserId(title, content, emotion)
//            if (response.error == true) {
//                emit(UiState.Error(response.message ?: "Unknown error occurred"))
//            } else {
//                emit(UiState.Success(response.data))
//            }
//        } catch (e: Exception) {
//            emit(UiState.Error(e.localizedMessage ?: "Error fetching journals"))
//        }
//    }

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

    fun postAnalyticData(request: AnalyticRequest): Flow<UiState<DataEmotion>> = flow {
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