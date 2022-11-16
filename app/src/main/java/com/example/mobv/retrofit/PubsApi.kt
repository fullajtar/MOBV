package com.example.mobv.retrofit

import retrofit2.Response
import retrofit2.http.*


interface PubsApi {
    @GET("/quotes")
    suspend fun getQuotes() : Response<QuoteList>

    @Headers(
        "Content-Type: application/json",
        "Access-Control-Request-Headers: *",
        "api-key: KHUu1Fo8042UwzczKz9nNeuVOsg2T4ClIfhndD2Su0G0LHHCBf0LnUF05L231J0M"
    )
    @POST("find")
    suspend fun getPubs( @Body body: BodyApi ) : Response<Documents>
//    "{ \"collection\": \"bars\", \"database\": \"mobvapp\", \"dataSource\": \"Cluster0\" }"
}