package com.chaev.fastfin.data.api

import com.chaev.fastfin.data.models.RatesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/v1/{date}")
    suspend fun getRates(
        @Path("date")
        date: String,
        @Query("access_key")
        accessKey: String,
        @Query("symbols")
        codes: String
    ): RatesResponse
}