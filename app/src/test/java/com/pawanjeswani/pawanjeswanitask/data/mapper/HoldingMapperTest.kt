package com.pawanjeswani.pawanjeswanitask.data.mapper

import com.pawanjeswani.pawanjeswanitask.data.remote.dto.HoldingDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class HoldingMapperTest {

    @Test
    fun `toHolding returns valid Holding when no fields are null`() {
        val dto = HoldingDto(
            symbol = "TCS",
            quantity = 10,
            ltp = 3200.0,
            avgPrice = 3000.0,
            close = 3250.0
        )

        val holding = dto.toHolding()

        assertEquals("TCS", holding?.symbol)
        assertEquals(10, holding?.quantity)
        assertEquals(3200.0, holding?.ltp ?: 0.0, 0.0)
        assertEquals(3000.0, holding?.avgPrice ?: 0.0, 0.0)
        assertEquals(3250.0, holding?.close ?: 0.0, 0.0)
    }

    @Test
    fun `toHolding returns null when any required field is null`() {
        val dtoWithoutSymbol = HoldingDto(null, 10, 3200.0, 3000.0, 3250.0)
        assertNull(dtoWithoutSymbol.toHolding())

        val dtoWithoutQuantity = HoldingDto("TCS", null, 3200.0, 3000.0, 3250.0)
        assertNull(dtoWithoutQuantity.toHolding())
        
        val dtoWithoutLtp = HoldingDto("TCS", 10, null, 3000.0, 3250.0)
        assertNull(dtoWithoutLtp.toHolding())

        val dtoWithoutAvgPrice = HoldingDto("TCS", 10, 3200.0, null, 3250.0)
        assertNull(dtoWithoutAvgPrice.toHolding())

        val dtoWithoutClose = HoldingDto("TCS", 10, 3200.0, 3000.0, null)
        assertNull(dtoWithoutClose.toHolding())
    }
}
