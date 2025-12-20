package com.pawanjeswani.pawanjeswanitask.domain.repository

import com.pawanjeswani.pawanjeswanitask.domain.model.Holding

interface HoldingsRepository {
    suspend fun getHoldings(): Result<List<Holding>>
}
