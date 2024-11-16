package io.mindset.jagamental.data.model.request

data class UserRequest(
    val fullname: String? = null,
    val gender: String? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    val avatar: String
)
