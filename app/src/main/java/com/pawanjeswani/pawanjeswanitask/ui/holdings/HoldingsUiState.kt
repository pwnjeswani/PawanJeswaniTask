package com.pawanjeswani.pawanjeswanitask.ui.holdings

import com.pawanjeswani.pawanjeswanitask.domain.model.Holding
import com.pawanjeswani.pawanjeswanitask.domain.model.PortfolioSummary

import com.pawanjeswani.pawanjeswanitask.util.UiText

// Sealed class representing possible UI states for holdings screen
sealed class HoldingsUiState {
    data object Loading : HoldingsUiState()
    
    data class Success(
        val holdings: List<Holding>,
        val summary: PortfolioSummary
    ) : HoldingsUiState()
    
    data class Error(
        val message: UiText
    ) : HoldingsUiState()
}
