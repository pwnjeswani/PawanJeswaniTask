package com.pawanjeswani.pawanjeswanitask.util

import java.text.NumberFormat
import java.util.Locale

// Utility object providing currency and percentage formatting functions
object Formatters {
    val currencyFormatter: NumberFormat by lazy { // Lazy-initialized Indian currency formatter
        NumberFormat.getCurrencyInstance(Locale.forLanguageTag("en-IN")).apply {
            maximumFractionDigits = 2
            minimumFractionDigits = 2
        }
    }

    // Formats amount as Indian currency, treating near-zero values as zero
    fun formatCurrency(amount: Double): String {
        val finalAmount = if (kotlin.math.abs(amount) < 0.005) 0.0 else amount
        return currencyFormatter.format(finalAmount)
    }

    // Formats value as percentage with 2 decimal places
    fun formatPercentage(value: Double): String {
        val finalValue = if (kotlin.math.abs(value) < 0.005) 0.0 else value
        return String.format(Locale.US, "%.2f", finalValue)
    }
}
