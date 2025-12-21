package com.pawanjeswani.pawanjeswanitask.data.mapper

import android.util.Log
import com.pawanjeswani.pawanjeswanitask.data.remote.dto.HoldingDto
import com.pawanjeswani.pawanjeswanitask.domain.model.Holding

fun HoldingDto.toHolding(): Holding? {
    return if (symbol != null && 
        quantity != null && 
        ltp != null && 
        avgPrice != null && 
        close != null
    ) {
        Holding(
            symbol = symbol,
            quantity = quantity,
            ltp = ltp,
            avgPrice = avgPrice,
            close = close
        )
    } else {
        try {
            Log.w("HoldingMapper", "Dropped holding with missing fields: symbol=$symbol")
        } catch (e: RuntimeException) {
            println("HoldingMapper: Dropped holding with missing fields: symbol=$symbol")
        }
        null
    }
}
