package io.mindset.jagamental.data.model.request

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @field:SerializedName("fullname")
    val fullname: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("avatar")
    val avatar: String? = null
)
