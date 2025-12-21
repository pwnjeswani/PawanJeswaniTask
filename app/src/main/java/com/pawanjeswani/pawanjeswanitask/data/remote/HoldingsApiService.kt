package com.pawanjeswani.pawanjeswanitask.data.remote

import com.pawanjeswani.pawanjeswanitask.data.remote.dto.HoldingResponse
import retrofit2.http.GET

// Retrofit interface for fetching holdings data from remote API
interface HoldingsApiService {
    
    // Fetches all user holdings from the API
    @GET("/")
    suspend fun getHoldings(): HoldingResponse
    
    companion object {
        const val BASE_URL = "https://35dee773a9ec441e9f38d5fc249406ce.api.mockbin.io"
    }
}
