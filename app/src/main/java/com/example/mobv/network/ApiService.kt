package com.example.mobv.network

import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

//private const val BASE_URL = "https://data.mongodb-api.com/app/data-fswjp/endpoint/data/v1/action/"
//private val moshi = Moshi.Builder()
//    .add(KotlinJsonAdapterFactory())
//    .build()
//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(MoshiConverterFactory.create(moshi))
//    .baseUrl(BASE_URL)
//    .build()
//
//interface FirmApiService {
//    @Headers(
//        "Content-Type: application/json",
//        "Access-Control-Request-Headers: *",
//        "api-key: KHUu1Fo8042UwzczKz9nNeuVOsg2T4ClIfhndD2Su0G0LHHCBf0LnUF05L231J0M"
//    )
//    @POST("find")
//    suspend fun getFirms(@Body firmApiServiceBody: FirmApiServiceBody = FirmApiServiceBody()): FirmContainerDto
//}