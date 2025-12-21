package com.pawanjeswani.pawanjeswanitask.domain.model

// Domain model representing a stock holding with computed financial metrics
data class Holding(
    val id: String = java.util.UUID.randomUUID().toString(),
    val symbol: String,
    val quantity: Int,
    val ltp: Double,
    val avgPrice: Double,
    val close: Double
) {
    val currentValue: Double // Current market value of holding
        get() = ltp * quantity
    
    val investment: Double // Total amount invested in this holding
        get() = avgPrice * quantity
    
    val pnl: Double // Total profit or loss on this holding
        get() = currentValue - investment
    
    val todayPnl: Double // Today's profit or loss based on closing price
        get() = (close - ltp) * quantity
}
