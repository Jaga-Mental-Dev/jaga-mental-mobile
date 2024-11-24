package io.mindset.jagamental.data.model.response

import com.google.gson.annotations.SerializedName

data class EmotionAnalyticResponse(
	@field:SerializedName("data")
	val data: DataEmotion? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataEmotion(
	@field:SerializedName("sedih")
	val sedih: List<Int>,

	@field:SerializedName("marah")
	val marah: List<Int>,

	@field:SerializedName("netral")
	val netral: List<Int>,

	@field:SerializedName("senang")
	val senang: List<Int>
)
