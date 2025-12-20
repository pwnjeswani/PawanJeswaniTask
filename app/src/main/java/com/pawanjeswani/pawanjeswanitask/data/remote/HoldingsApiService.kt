package com.pawanjeswani.pawanjeswanitask.data.remote

import com.pawanjeswani.pawanjeswanitask.data.remote.dto.HoldingResponse
import retrofit2.http.GET

interface HoldingsApiService {
    
    @GET("/")
    suspend fun getHoldings(): HoldingResponse
    
    companion object {
        const val BASE_URL = "https://35dee773a9ec441e9f38d5fc249406ce.api.mockbin.io"
    }
}
