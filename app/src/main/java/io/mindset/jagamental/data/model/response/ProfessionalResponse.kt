package io.mindset.jagamental.data.model.response

import com.google.gson.annotations.SerializedName
import io.mindset.jagamental.data.model.ProfessionalProfile

data class ProfessionalResponse(

    @field:SerializedName("data")
    val data: List<ProfessionalProfile>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)