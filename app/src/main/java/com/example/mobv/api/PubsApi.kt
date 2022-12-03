package com.example.mobv.api

import android.content.Context
import com.example.mobv.BuildConfig
import com.example.mobv.model.Bar
import com.example.mobv.model.Pubs
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
    )
    @POST("user/create.php")
    suspend fun signUp( @Body body: BodySignUp ) : Response<UserResponse>

    @Headers(
        "Content-Type: application/json",
        "Cache-Control: no-cache",
        "Content-Type: application/json",
    )
    @POST("user/login.php")
    suspend fun signIn( @Body body: BodySignUp ) : Response<UserResponse>

    @POST("user/refresh.php")
    fun userRefresh(@Body user: UserRefreshRequest) : Call<UserResponse>

    @GET("bar/list.php")
    @Headers("mobv-auth: accept")
    suspend fun barList() : Response<List<BarListResponse>>

    @POST("contact/message.php")
    @Headers("mobv-auth: accept")
    suspend fun addFriend( @Body contact: BodyAddFriend) : Response<*>

    @GET("contact/list.php")
    @Headers("mobv-auth: accept")
    suspend fun getFriendList() : Response<List<FriendResponse>>

    @GET("https://overpass-api.de/api/interpreter?")
    suspend fun barNearby(@Query("data") data: String): Response<Pubs>

    @POST("bar/message.php")
    @Headers("mobv-auth: accept")
    suspend fun checkInBar(@Body bar: BodyCheckInBar) : Response<Any>

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