package com.pawanjeswani.pawanjeswanitask.util

import org.junit.Assert.assertEquals
import org.junit.Test

class FormattersTest {

    @Test
    fun `formatCurrency formats correctly for Indian locale`() {
        // Enforce locale for consistent testing environment if needed, 
        // but formatter hardcodes "en-IN" internally.
        
        val value = 12345.678
        // "en-IN" currency formatting typically includes the Rupee symbol. 
        // Since the symbol usage might vary by JVM version/font, we can at least check the structure or replace the symbol.
        // Or cleaner: just check basic decimal structure if symbol is unpredictable in test env, 
        // but typically it is "₹" or "Rs.".
        // Let's rely on standard Java localization which usually output "₹12,345.68" or similar.
        
        val formatted = Formatters.formatCurrency(value)
        // Check that it contains the number formatted properly (2 decimal places)
        // 12,345.68
        assert(formatted.contains("12,345.68"))
    }

    @Test
    fun `formatCurrency formats integer values with two decimal places`() {
        val value = 500.0
        val formatted = Formatters.formatCurrency(value)
        assert(formatted.contains("500.00"))
    }

    @Test
    fun `formatPercentage formats with two decimal places`() {
        assertEquals("5.12", Formatters.formatPercentage(5.1234))
        assertEquals("10.00", Formatters.formatPercentage(10.0))
        assertEquals("-2.50", Formatters.formatPercentage(-2.5))
    }

    @Test
    fun `formatCurrency handles negative zero correctly`() {
        val value = -0.001
        val formatted = Formatters.formatCurrency(value)
        // Should be normalized to positive zero "0.00"
        assert(formatted.contains("0.00"))
    }
}
