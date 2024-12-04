package io.mindset.jagamental.di

import android.content.Context
import android.content.SharedPreferences
import com.chuckerteam.chucker.api.ChuckerInterceptor
import io.mindset.jagamental.data.domain.AuthRepository
import io.mindset.jagamental.data.domain.JournalRepository
import io.mindset.jagamental.data.domain.LoginRepository
import io.mindset.jagamental.data.remote.ApiService
import io.mindset.jagamental.utils.SharedPreferencesHelper
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val apiUrl = "https://jamen-api-616607546235.asia-southeast2.run.app/api/"

val networkModule = module {
    single {
        val authRepository: AuthRepository = get()
        val authInterceptor = createAuthInterceptor(authRepository)

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ChuckerInterceptor(get()))
            .addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

private fun createAuthInterceptor(authRepository: AuthRepository): Interceptor {
    return Interceptor { chain ->
        val token = runBlocking { authRepository.getIdTokenFromFirebase() }
        val req = chain.request()
        val requestHeaders = req.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        chain.proceed(requestHeaders)
    }
}

val repositoryModule = module {
    single { AuthRepository(get(), get()) }
    single { JournalRepository(apiService = get()) }
    single { LoginRepository(apiService = get()) }
}

val sharedPreferencesModule = module {
    single<SharedPreferences> {
        get<Context>().getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    }
    single { SharedPreferencesHelper(get()) }
}