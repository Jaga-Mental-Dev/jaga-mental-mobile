package io.mindset.jagamental.data.model.request

data class RegisterRequest (
    val fullname: String,
    val email: String,
    val password: String,
)

data class LoginRequest (
    val email: String,
    val password: String,
)