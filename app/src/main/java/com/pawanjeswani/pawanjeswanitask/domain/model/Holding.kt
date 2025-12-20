package com.pawanjeswani.pawanjeswanitask.domain.model

data class Holding(
    val symbol: String,
    val quantity: Int,
    val ltp: Double,
    val avgPrice: Double,
    val close: Double
) {
    val currentValue: Double
        get() = ltp * quantity
    
    val investment: Double
        get() = avgPrice * quantity
    
    val pnl: Double
        get() = currentValue - investment
    
    val todayPnl: Double
        get() = (close - ltp) * quantity
}
