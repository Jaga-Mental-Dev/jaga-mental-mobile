package io.mindset.jagamental.data.remote

import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @POST("/auth/login")
    @FormUrlEncoded
    suspend fun doLogin(

    )

    @POST("/auth/register")
    @FormUrlEncoded
    suspend fun doRegister(

    )

    @POST("/oauth")
    @FormUrlEncoded
    suspend fun doOauth(

    )

    @PUT("/user/{id}")
    @FormUrlEncoded
    suspend fun doUpdateUser(

    )

    @GET("/user/me")
    suspend fun doGetCurrentUser(

    )
}