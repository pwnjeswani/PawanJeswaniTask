package com.pawanjeswani.pawanjeswanitask.domain.model

// Domain model representing aggregated portfolio summary with computed percentages
data class PortfolioSummary(
    val currentValue: Double,
    val totalInvestment: Double,
    val totalPnl: Double,
    val todayPnl: Double
) {
    val totalPnlPercentage: Double // Total P&L as a percentage of investment
        get() = if (totalInvestment != 0.0) (totalPnl / totalInvestment) * 100 else 0.0
    
    val todayPnlPercentage: Double // Today's P&L as a percentage of current value
        get() = if (currentValue != 0.0) (todayPnl / currentValue) * 100 else 0.0
}
