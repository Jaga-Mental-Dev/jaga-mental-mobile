package io.mindset.jagamental.data.model.request

import com.google.gson.annotations.SerializedName

data class JournalRequest(
    @field:SerializedName("title")
    val title: String? = null,
    @field:SerializedName("content")
    val content: String? = null,
    @field:SerializedName("date")
    val date: String? = null,
)