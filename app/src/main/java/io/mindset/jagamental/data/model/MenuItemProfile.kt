package io.mindset.jagamental.data.model

data class MenuItemProfile(
    val icon: Int,
    val title: String,
    val onClick: () -> Unit,
    val url: String? = null
)