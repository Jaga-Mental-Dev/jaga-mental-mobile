package io.mindset.jagamental.data.model.response

import com.google.gson.annotations.SerializedName

data class JournalResponse(
	@field:SerializedName("data")
	val data: List<JournalDataItem>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class JournalDataItem(
	@field:SerializedName("emotion")
	val emotion: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Long? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("content")
	val content: String? = null
)
