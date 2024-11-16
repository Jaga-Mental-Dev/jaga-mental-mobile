package io.mindset.jagamental.data.model.request

data class JournalRequest (
    val title: String? = null,
    val content: String? = null,
    val emotion: String? = null,
    val selfie: String? = null,
)