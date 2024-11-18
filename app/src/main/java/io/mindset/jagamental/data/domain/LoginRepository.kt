package io.mindset.jagamental.data.domain

import io.mindset.jagamental.data.model.request.LoginRequest
import io.mindset.jagamental.data.model.request.RegisterRequest
import io.mindset.jagamental.data.model.request.UserRequest
import io.mindset.jagamental.data.model.response.AuthResponse
import io.mindset.jagamental.data.remote.ApiService
import io.mindset.jagamental.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepository(private val apiService: ApiService) {
    fun loginUser(request: LoginRequest): Flow<UiState<AuthResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.doLogin(request)
            if (response.error == true) {
                emit(UiState.Error(response.message ?: "Login failed"))
            } else {
                emit(UiState.Success(response))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Error during login"))
        }
    }

    // Function for user registration
    fun registerUser(request: RegisterRequest): Flow<UiState<AuthResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.doRegister(request)
            if (response.error == true) {
                emit(UiState.Error(response.message ?: "Registration failed"))
            } else {
                emit(UiState.Success(response))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Error during registration"))
        }
    }

    // Function for OAuth
    fun oauth(): Flow<UiState<AuthResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.doOauth()
            if (response.error == true) {
                emit(UiState.Error(response.message ?: "OAuth failed"))
            } else {
                emit(UiState.Success(response))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Error during OAuth"))
        }
    }

    // Function to get user by ID
    fun getUserById(id: String): Flow<UiState<AuthResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.doGetUserById(id)
            if (response.error == true) {
                emit(UiState.Error(response.message ?: "User not found"))
            } else {
                emit(UiState.Success(response))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Error fetching user data"))
        }
    }

    // Function to get the current user
    fun getCurrentUser(): Flow<UiState<AuthResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.doGetCurrentUser()
            if (response.error == true) {
                emit(UiState.Error(response.message ?: "Failed to retrieve current user"))
            } else {
                emit(UiState.Success(response))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Error retrieving current user"))
        }
    }

    // Function to get all users
    fun getAllUsers(): Flow<UiState<AuthResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.doGetAllUser()
            if (response.error == true) {
                emit(UiState.Error(response.message ?: "Failed to retrieve users"))
            } else {
                emit(UiState.Success(response))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Error retrieving users"))
        }
    }

    // Function to update user
    fun updateUser(userRequest: UserRequest): Flow<UiState<AuthResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.doUpdateUser(userRequest)
            if (response.error == true) {
                emit(UiState.Error(response.message ?: "Update failed"))
            } else {
                emit(UiState.Success(response))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Error updating user"))
        }
    }

    // Function to delete user
    fun deleteUser(userId: String): Flow<UiState<AuthResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.doDeleteUser(userId)
            if (response.error == true) {
                emit(UiState.Error(response.message ?: "Deletion failed"))
            } else {
                emit(UiState.Success(response))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Error deleting user"))
        }
    }

}