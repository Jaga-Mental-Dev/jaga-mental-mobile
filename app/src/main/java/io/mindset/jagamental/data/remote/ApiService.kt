package io.mindset.jagamental.data.remote

import io.mindset.jagamental.data.model.request.JournalRequest
import io.mindset.jagamental.data.model.request.UserRequest
import io.mindset.jagamental.data.model.response.AuthResponse
import io.mindset.jagamental.data.model.response.EmotionAnalyticResponse
import io.mindset.jagamental.data.model.response.JournalResponse
import io.mindset.jagamental.data.model.response.ListJournalResponse
import io.mindset.jagamental.data.model.response.ProfessionalResponse
import io.mindset.jagamental.data.model.response.UserResponse
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

interface ApiService {
    // Users
    @GET("/user/me")
    suspend fun doGetCurrentUser(): UserResponse

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
    @POST("/api/auth/oauth")
    suspend fun doOauth(): AuthResponse

    @POST("/local")
    suspend fun doLocal(): AuthResponse

    @GET("/api/journal")
    suspend fun doGetJournalByUserId(): ListJournalResponse

    @Multipart
    @POST("/api/journal")
    suspend fun doCreateJournal(
        @Part image: MultipartBody.Part
    ): JournalResponse

    @PUT("/api/journal/{id}")
    suspend fun doUpdateJournal(
        @Path("id") id: String,
        @Body contentRequest: JournalRequest
    ): JournalResponse

    @GET("/api/journal/{id}")
    suspend fun doJournalById(
        @Path("id") id: String
    ): JournalResponse

    @DELETE("/api/journal/{id}")
    suspend fun doDeleteJournal(
        @Path("id") id: String
    ): JournalResponse

    @POST("/api/journal/date")
    suspend fun doGetJournalByDate(
        @Body request: JournalRequest
    ): ListJournalResponse

    // Analytic
    @GET("/api/analytic")
    suspend fun doPostAnalytic(): EmotionAnalyticResponse

    // Professional
    @GET("/api/professionals")
    suspend fun doGetProfessional(): ProfessionalResponse
}