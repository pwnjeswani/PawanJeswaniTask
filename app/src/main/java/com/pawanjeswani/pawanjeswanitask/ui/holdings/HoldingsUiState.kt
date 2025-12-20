package com.pawanjeswani.pawanjeswanitask.ui.holdings

import com.pawanjeswani.pawanjeswanitask.domain.model.Holding
import com.pawanjeswani.pawanjeswanitask.domain.model.PortfolioSummary

sealed class HoldingsUiState {
    data object Loading : HoldingsUiState()
    
    data class Success(
        val holdings: List<Holding>,
        val summary: PortfolioSummary
    ) : HoldingsUiState()
    
    data class Error(
        val message: String
    ) : HoldingsUiState()
}
