package com.stc.data.remote

import com.stc.domain.entity.CurrenciesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("latest")
    suspend fun getLatest(@Query("access_key") accessKey : String): CurrenciesResponse
}