package io.mindset.jagamental.data.domain

import android.content.Context
import io.mindset.jagamental.data.remote.ApiService

class MainRepository(
    private val apiService: ApiService,
    private val context: Context
) {
}