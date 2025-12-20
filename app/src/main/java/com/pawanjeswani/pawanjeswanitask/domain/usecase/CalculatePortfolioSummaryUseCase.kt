package com.pawanjeswani.pawanjeswanitask.domain.usecase

import com.pawanjeswani.pawanjeswanitask.domain.model.Holding
import com.pawanjeswani.pawanjeswanitask.domain.model.PortfolioSummary
import javax.inject.Inject

class CalculatePortfolioSummaryUseCase @Inject constructor() {
    
    operator fun invoke(holdings: List<Holding>): PortfolioSummary {
        if (holdings.isEmpty()) {
            return PortfolioSummary(
                currentValue = 0.0,
                totalInvestment = 0.0,
                totalPnl = 0.0,
                todayPnl = 0.0
            )
        }
        
        // Current Value: Sum of (LTP * Quantity) for all holdings
        val currentValue = holdings.sumOf { it.ltp * it.quantity }
        
        // Total Investment: Sum of (Average Price * Quantity) for all holdings
        val totalInvestment = holdings.sumOf { it.avgPrice * it.quantity }
        
        // Total PNL: Current Value - Total Investment
        val totalPnl = currentValue - totalInvestment
        
        // Today's PNL: Sum of ((Close - LTP) * Quantity) for all holdings
        val todayPnl = holdings.sumOf { (it.close - it.ltp) * it.quantity }
        
        return PortfolioSummary(
            currentValue = currentValue,
            totalInvestment = totalInvestment,
            totalPnl = totalPnl,
            todayPnl = todayPnl
        )
    }
}
