package com.pawanjeswani.pawanjeswanitask.domain.model

data class PortfolioSummary(
    val currentValue: Double,
    val totalInvestment: Double,
    val totalPnl: Double,
    val todayPnl: Double
) {
    val totalPnlPercentage: Double
        get() = if (totalInvestment != 0.0) (totalPnl / totalInvestment) * 100 else 0.0
    
    val todayPnlPercentage: Double
        get() = if (currentValue != 0.0) (todayPnl / currentValue) * 100 else 0.0
}
