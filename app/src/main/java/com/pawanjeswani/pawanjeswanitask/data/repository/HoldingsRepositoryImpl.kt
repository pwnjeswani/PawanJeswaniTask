package com.pawanjeswani.pawanjeswanitask.data.repository

import com.pawanjeswani.pawanjeswanitask.data.remote.HoldingsApiService
import com.pawanjeswani.pawanjeswanitask.domain.model.Holding
import com.pawanjeswani.pawanjeswanitask.domain.repository.HoldingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HoldingsRepositoryImpl @Inject constructor(
    private val apiService: HoldingsApiService
) : HoldingsRepository {
    
    override suspend fun getHoldings(): Result<List<Holding>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getHoldings()
            val holdings = response.data?.userHolding?.mapNotNull { dto ->
                // Handle null values gracefully
                if (dto.symbol != null && 
                    dto.quantity != null && 
                    dto.ltp != null && 
                    dto.avgPrice != null && 
                    dto.close != null) {
                    Holding(
                        symbol = dto.symbol,
                        quantity = dto.quantity,
                        ltp = dto.ltp,
                        avgPrice = dto.avgPrice,
                        close = dto.close
                    )
                } else {
                    null
                }
            } ?: emptyList()
            Result.success(holdings)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
