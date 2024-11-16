package io.mindset.jagamental.data.model.response

import com.google.gson.annotations.SerializedName

data class EmotionResponse(

	@field:SerializedName("data")
	val data: List<EmotionDataItem>,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class EmotionDataItem(

	@field:SerializedName("emotion_count")
	val emotionCount: Int? = null,

	@field:SerializedName("emotion")
	val emotion: String? = null
)
