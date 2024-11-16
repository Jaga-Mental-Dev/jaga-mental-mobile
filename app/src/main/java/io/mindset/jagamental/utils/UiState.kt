package io.mindset.jagamental.utils

sealed class UiState<out T: Any?> {

    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val ignoredError: String) : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
}