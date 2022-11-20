package com.example.mobv.api

import com.example.mobv.BuildConfig
import retrofit2.Response
import retrofit2.http.*


interface PubsApi {
    @Headers(
        "Content-Type: application/json",
        "Cache-Control: no-cache",
        "Content-Type: application/json",
        "x-apikey: ${BuildConfig.API_KEY}"
    )
    @POST("create.php")
    suspend fun signUp( @Body body: BodySignUp ) : Response<UserResponse>

    @Headers(
        "Content-Type: application/json",
        "Cache-Control: no-cache",
        "Content-Type: application/json",
        "x-apikey: ${BuildConfig.API_KEY}"
    )
    @POST("login.php")
    suspend fun signIn( @Body body: BodySignUp ) : Response<UserResponse>
}