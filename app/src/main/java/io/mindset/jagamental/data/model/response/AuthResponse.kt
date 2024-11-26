package io.mindset.jagamental.data.model.response


import com.google.gson.annotations.SerializedName

data class AuthResponse(
    val user: User
)

data class User(
    val avatar: String,
    @SerializedName("created_at")
    val createdAt: String,
    val email: String,
    @SerializedName("firebase_id")
    val firebaseId: String,
    @SerializedName("full_name")
    val fullName: String,
    val gender: Any,
    val id: String,
    val password: Any
)