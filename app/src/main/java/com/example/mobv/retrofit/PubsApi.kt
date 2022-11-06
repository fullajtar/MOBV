package com.example.mobv.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PubsApi {
    @GET("/quotes")
    suspend fun getQuotes() : Response<QuoteList>
}