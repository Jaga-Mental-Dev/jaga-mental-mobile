package io.mindset.jagamental.data.model.response

import com.google.gson.annotations.SerializedName

data class ListJournalResponse(

    @field:SerializedName("data")
    val data: List<JournalData?>? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)