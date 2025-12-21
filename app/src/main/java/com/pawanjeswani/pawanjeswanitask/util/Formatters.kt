package com.pawanjeswani.pawanjeswanitask.util

import java.text.NumberFormat
import java.util.Locale

object Formatters {
    val currencyFormatter: NumberFormat by lazy {
        NumberFormat.getCurrencyInstance(Locale.forLanguageTag("en-IN")).apply {
            maximumFractionDigits = 2
            minimumFractionDigits = 2
        }
    }

    fun formatCurrency(amount: Double): String {
        val finalAmount = if (kotlin.math.abs(amount) < 0.005) 0.0 else amount
        return currencyFormatter.format(finalAmount)
    }

    fun formatPercentage(value: Double): String {
        val finalValue = if (kotlin.math.abs(value) < 0.005) 0.0 else value
        return String.format(Locale.US, "%.2f", finalValue)
    }
}
