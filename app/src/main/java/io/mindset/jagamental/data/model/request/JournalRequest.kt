package io.mindset.jagamental.data.model.request

import com.google.gson.annotations.SerializedName

data class JournalRequest (
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("content")
    val content: String,
)