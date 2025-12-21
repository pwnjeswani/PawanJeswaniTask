package com.pawanjeswani.pawanjeswanitask.domain.repository

import com.pawanjeswani.pawanjeswanitask.domain.model.Holding

// Repository interface for fetching holdings data
interface HoldingsRepository {
    suspend fun getHoldings(): Result<List<Holding>>
}
