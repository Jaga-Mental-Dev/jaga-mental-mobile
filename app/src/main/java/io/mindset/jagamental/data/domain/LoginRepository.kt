package io.mindset.jagamental.data.domain

import android.util.Log
import io.mindset.jagamental.data.model.response.AuthResponse
import io.mindset.jagamental.data.remote.ApiService
import io.mindset.jagamental.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepository(private val apiService: ApiService) {

    // Function to get the current user
//    fun getCurrentUser(): Flow<UiState<AuthResponse>> = flow {
//        emit(UiState.Loading)
//        try {
//            val response = apiService.doGetCurrentUser()
//            if (response == true) {
//                emit(UiState.Error(response.message ?: "Failed to retrieve current user"))
//            } else {
//                emit(UiState.Success(response))
//            }
//        } catch (e: Exception) {
//            emit(UiState.Error(e.localizedMessage ?: "Error retrieving current user"))
//        }
//    }

    // Function to update user
//    fun updateUser(userId: String, userRequest: UserRequest): Flow<UiState<AuthResponse>> = flow {
//        emit(UiState.Loading)
//        try {
//            val response = apiService.doUpdateUser(userId, userRequest)
//            if (response.error == true) {
//                emit(UiState.Error(response.message ?: "Update failed"))
//            } else {
//                emit(UiState.Success(response))
//            }
//        } catch (e: Exception) {
//            emit(UiState.Error(e.localizedMessage ?: "Error updating user"))
//        }
//    }

    // Function to delete user
//    fun deleteUser(userId: String): Flow<UiState<AuthResponse>> = flow {
//        emit(UiState.Loading)
//        try {
//            val response = apiService.doDeleteUser(userId)
//            if (response.error == true) {
//                emit(UiState.Error(response.message ?: "Deletion failed"))
//            } else {
//                emit(UiState.Success(response))
//            }
//        } catch (e: Exception) {
//            emit(UiState.Error(e.localizedMessage ?: "Error deleting user"))
//        }
//    }

    // Function to get user by ID
//    fun getUserById(id: String): Flow<UiState<AuthResponse>> = flow {
//        emit(UiState.Loading)
//        try {
//            val response = apiService.doGetUserById(id)
//            if (response.error == true) {
//                emit(UiState.Error(response.message ?: "User not found"))
//            } else {
//                emit(UiState.Success(response))
//            }
//        } catch (e: Exception) {
//            emit(UiState.Error(e.localizedMessage ?: "Error fetching user data"))
//        }
//    }

    // Function for OAuth
    fun postAuth(): Flow<UiState<AuthResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.doOauth()
            emit(UiState.Success(response))
        } catch (e: Exception) {
            Log.d("LoginRepository", "postAuth: ${e.message}")
            emit(UiState.Error(e.localizedMessage ?: "Error during OAuth"))
        }
    }

    // Function for Local
//    fun postLocal(): Flow<UiState<AuthResponse>> = flow {
//        emit(UiState.Loading)
//        try {
//            val response = apiService.doLocal()
//            if (response.error == true) {
//                emit(UiState.Error(response.message ?: "OAuth failed"))
//            } else {
//                emit(UiState.Success(response))
//            }
//        } catch (e: Exception) {
//            emit(UiState.Error(e.localizedMessage ?: "Error during OAuth"))
//        }
//    }
}