package com.pawanjeswani.pawanjeswanitask.data.repository

import com.pawanjeswani.pawanjeswanitask.data.mapper.toHolding
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
                dto.toHolding()
            } ?: emptyList()
            Result.success(holdings)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
