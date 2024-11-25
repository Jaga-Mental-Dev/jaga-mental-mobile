package io.mindset.jagamental.data.remote

import io.mindset.jagamental.data.model.request.AnalyticRequest
import io.mindset.jagamental.data.model.request.JournalRequest
import io.mindset.jagamental.data.model.request.UserRequest
import io.mindset.jagamental.data.model.response.AuthResponse
import io.mindset.jagamental.data.model.response.EmotionAnalyticResponse
import io.mindset.jagamental.data.model.response.JournalResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // Users
    @GET("/user/me")
    suspend fun doGetCurrentUser(): AuthResponse

    @PUT("/user/{id}")
    @FormUrlEncoded
    suspend fun doUpdateUser(
        @Path("id") id: String,
        @Body userRequest: UserRequest
    ): AuthResponse

    @DELETE("/user/{id}")
    suspend fun doDeleteUser(
        @Path("id") userId: String
    ): AuthResponse

    @GET("/user/{id}")
    suspend fun doGetUserById(
        @Path("id") id: String
    ): AuthResponse

    // Auth
    @POST("/oauth")
    suspend fun doOauth(): AuthResponse

    @POST("/local")
    suspend fun doLocal(): AuthResponse

    // Journal
    @GET("/journal")
    suspend fun doGetJournalByUserId(
        @Query("title") title: String? = null,
        @Query("content") content: String? = null,
        @Query("emotion") emotion: String? = null
    ): JournalResponse

    @Multipart
    @POST("/journal")
    suspend fun doCreateJournal(
        @Part image: MultipartBody.Part
    ): JournalResponse

    @FormUrlEncoded
    @PUT("/journal/{id}")
    suspend fun doUpdateJournal(
        @Path("id") id: String,
        @Body contentRequest: JournalRequest
    ): JournalResponse

    @GET("/journal/{id}")
    suspend fun doJournalById(
        @Path("id") id: String
    ): JournalResponse

    @DELETE("/journal/{id}")
    suspend fun doDeleteJournal(
        @Path("id") id: String
    ): JournalResponse

    @POST("/journal/date")
    suspend fun doGetJournalByDate(
        @Query("date") date: String
    ): JournalResponse

    // Analytic
    @GET("/analytic")
    suspend fun doPostAnalytic(
        @Body request: AnalyticRequest
    ): EmotionAnalyticResponse
}