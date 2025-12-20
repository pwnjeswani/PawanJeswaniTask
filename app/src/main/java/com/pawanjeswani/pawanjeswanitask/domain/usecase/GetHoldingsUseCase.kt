package com.pawanjeswani.pawanjeswanitask.domain.usecase

import com.pawanjeswani.pawanjeswanitask.domain.model.Holding
import com.pawanjeswani.pawanjeswanitask.domain.repository.HoldingsRepository
import javax.inject.Inject

class GetHoldingsUseCase @Inject constructor(
    private val repository: HoldingsRepository
) {
    suspend operator fun invoke(): Result<List<Holding>> {
        return repository.getHoldings()
    }
}
