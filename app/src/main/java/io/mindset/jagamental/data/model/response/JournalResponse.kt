package io.mindset.jagamental.data.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class JournalResponse(

    @field:SerializedName("data")
    val data: JournalData? = null,

    @field:SerializedName("error")
    val error: Boolean = false,

    @field:SerializedName("message")
    val message: String? = null
)

@Serializable
data class JournalData(

    @field:SerializedName("feedback")
    val feedback: String? = null,

    @field:SerializedName("emotion")
    val emotion: String? = null,

    @field:SerializedName("user_id")
    val userId: String? = null,

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("initialEmotion")
    val initialEmotion: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("content")
    val content: String? = null
)