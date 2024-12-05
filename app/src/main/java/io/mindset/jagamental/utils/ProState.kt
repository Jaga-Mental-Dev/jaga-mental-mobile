package io.mindset.jagamental.utils

sealed class ProState<out T : Any?> {
    data class Success<out T>(val data: T) : ProState<T>()
    data class Error(val message: String) : ProState<Nothing>()
    data object Loading : ProState<Nothing>()
    data object Idle : ProState<Nothing>()
}