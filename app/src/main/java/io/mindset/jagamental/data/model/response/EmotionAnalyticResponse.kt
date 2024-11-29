package io.mindset.jagamental.data.model.response

import com.google.gson.annotations.SerializedName
import io.mindset.jagamental.data.model.ChartData

data class EmotionAnalyticResponse(
	@field:SerializedName("data")
    val data: List<ChartData?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
