package io.mindset.jagamental.data.model

import com.google.gson.annotations.SerializedName

data class ProfessionalProfile(
    @field:SerializedName("city")
    val city: String,

    @field:SerializedName("specialist")
    val specialist: String,

    @field:SerializedName("phone")
    val phone: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("avatar")
    val avatar: String
)