package io.mindset.jagamental.data.model.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(

    @field:SerializedName("data")
    val data: UserResponse? = null,

    @field:SerializedName("idToken")
    val idToken: String? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class UserResponse(

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("lon")
    val lon: Double? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("fullname")
    val fullname: String? = null,

    @field:SerializedName("avatar")
    val avatar: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("lat")
    val lat: Double? = null
)
