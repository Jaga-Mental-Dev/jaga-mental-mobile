package io.mindset.jagamental.data.remote

import io.mindset.jagamental.data.model.request.AnalyticRequest
import io.mindset.jagamental.data.model.request.LoginRequest
import io.mindset.jagamental.data.model.request.RegisterRequest
import io.mindset.jagamental.data.model.response.AuthResponse
import io.mindset.jagamental.data.model.response.JournalResponse
import io.mindset.jagamental.data.model.request.UserRequest
import io.mindset.jagamental.data.model.response.EmotionResponse
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
    @POST("/auth/login")
    suspend fun doLogin(
        @Body request: LoginRequest
    ): AuthResponse

    @POST("/auth/register")
    @FormUrlEncoded
    suspend fun doRegister(
        @Body request: RegisterRequest
    ): AuthResponse

    @POST("/oauth")
    @FormUrlEncoded
    suspend fun doOauth(): AuthResponse

    @GET("/user/{id}")
    suspend fun doGetUserById(
        @Path("id") id: String
    ): AuthResponse

    @GET("/user/me")
    suspend fun doGetCurrentUser(): AuthResponse

    @GET("/users")
    suspend fun doGetAllUser(): AuthResponse

    @PUT("/user/{id}")
    suspend fun doUpdateUser(
        @Body userRequest: UserRequest
    ): AuthResponse

    @DELETE("/user/{id}")
    suspend fun doDeleteUser(
        @Path("id") userId: String
    ): AuthResponse

    @GET("/journal")
    suspend fun doGetJournalByUserId(
        @Query("title") title: String? = null,
        @Query("content") content: String? = null,
        @Query("emotion") emotion: String? = null
    ): JournalResponse

    @GET("/journal/{id}")
    suspend fun doJournalById(
        @Path("id") id: String
    ): JournalResponse

    @Multipart
    @POST("/journal")
    suspend fun doCreateJournal(
        @Part("title") title: String? = null,
        @Part("content") content: String? = null,
        @Part("emotion") emotion: String? = null,
        @Part selfie: MultipartBody.Part
    ): JournalResponse

    @Multipart
    @PUT("/journal/{id}")
    suspend fun doUpdateJournal(
        @Path("id") id: String,
        @Part("title") title: String? = null,
        @Part("content") content: String? = null,
        @Part("emotion") emotion: String? = null,
        @Part("selfie") selfie: MultipartBody.Part
    ): JournalResponse

    @DELETE("/journal/{id}")
    suspend fun doDeleteJournal(
        @Path("id") id: String
    ): JournalResponse

    @POST("/journal/date")
    suspend fun doGetJournalByDate(
        @Query("date") date: String
    ): JournalResponse

    @POST("/analytic")
    suspend fun doPostAnalytic(
        @Body request: AnalyticRequest
    ): EmotionResponse
}