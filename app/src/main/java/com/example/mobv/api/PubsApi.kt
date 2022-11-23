package com.example.mobv.api

import android.content.Context
import com.example.mobv.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface PubsApi {
    @Headers(
        "Content-Type: application/json",
        "Cache-Control: no-cache",
        "Content-Type: application/json",
        "x-apikey: ${BuildConfig.API_KEY}"
    )
    @POST("user/create.php")
    suspend fun signUp( @Body body: BodySignUp ) : Response<UserResponse>

    @Headers(
        "Content-Type: application/json",
        "Cache-Control: no-cache",
        "Content-Type: application/json",
        "x-apikey: ${BuildConfig.API_KEY}"
    )
    @POST("user/login.php")
    suspend fun signIn( @Body body: BodySignUp ) : Response<UserResponse>

    @POST("user/refresh.php")
    fun userRefresh(@Body user: UserRefreshRequest) : Call<UserResponse>

    companion object{
        const val baseUrl = "https://zadanie.mpage.sk/"

        fun getInstance(): Retrofit {
            return Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                // we need to add converter factory to
                // convert JSON object to Java object
                .build()
        }

        fun create(context: Context): PubsApi {
            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .authenticator(TokenAuthenticator(context))
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(PubsApi::class.java)
        }
    }
}