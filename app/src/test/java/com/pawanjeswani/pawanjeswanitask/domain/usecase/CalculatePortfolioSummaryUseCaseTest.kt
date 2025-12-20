package com.pawanjeswani.pawanjeswanitask.domain.usecase

import com.pawanjeswani.pawanjeswanitask.domain.model.Holding
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CalculatePortfolioSummaryUseCaseTest {
    
    private lateinit var useCase: CalculatePortfolioSummaryUseCase
    
    @Before
    fun setup() {
        useCase = CalculatePortfolioSummaryUseCase()
    }
    
    @Test
    fun `empty holdings list returns zero summary`() {
        val result = useCase(emptyList())
        
        assertEquals(0.0, result.currentValue, 0.001)
        assertEquals(0.0, result.totalInvestment, 0.001)
        assertEquals(0.0, result.totalPnl, 0.001)
        assertEquals(0.0, result.todayPnl, 0.001)
    }
    
    @Test
    fun `single holding calculates current value correctly`() {
        // Current Value = LTP * Quantity = 100 * 10 = 1000
        val holdings = listOf(
            Holding(
                symbol = "TEST",
                quantity = 10,
                ltp = 100.0,
                avgPrice = 90.0,
                close = 110.0
            )
        )
        
        val result = useCase(holdings)
        
        assertEquals(1000.0, result.currentValue, 0.001)
    }
    
    @Test
    fun `single holding calculates total investment correctly`() {
        // Total Investment = Avg Price * Quantity = 90 * 10 = 900
        val holdings = listOf(
            Holding(
                symbol = "TEST",
                quantity = 10,
                ltp = 100.0,
                avgPrice = 90.0,
                close = 110.0
            )
        )
        
        val result = useCase(holdings)
        
        assertEquals(900.0, result.totalInvestment, 0.001)
    }
    
    @Test
    fun `single holding calculates total PNL correctly`() {
        // Total PNL = Current Value - Total Investment = 1000 - 900 = 100
        val holdings = listOf(
            Holding(
                symbol = "TEST",
                quantity = 10,
                ltp = 100.0,
                avgPrice = 90.0,
                close = 110.0
            )
        )
        
        val result = useCase(holdings)
        
        assertEquals(100.0, result.totalPnl, 0.001)
    }
    
    @Test
    fun `single holding calculates today PNL correctly`() {
        // Today's PNL = (Close - LTP) * Quantity = (110 - 100) * 10 = 100
        val holdings = listOf(
            Holding(
                symbol = "TEST",
                quantity = 10,
                ltp = 100.0,
                avgPrice = 90.0,
                close = 110.0
            )
        )
        
        val result = useCase(holdings)
        
        assertEquals(100.0, result.todayPnl, 0.001)
    }
    
    @Test
    fun `negative PNL calculated correctly`() {
        // Total PNL = (50*10) - (100*10) = 500 - 1000 = -500
        val holdings = listOf(
            Holding(
                symbol = "LOSS",
                quantity = 10,
                ltp = 50.0,
                avgPrice = 100.0,
                close = 40.0
            )
        )
        
        val result = useCase(holdings)
        
        assertEquals(-500.0, result.totalPnl, 0.001)
    }
    
    @Test
    fun `negative today PNL calculated correctly`() {
        // Today's PNL = (Close - LTP) * Quantity = (40 - 50) * 10 = -100
        val holdings = listOf(
            Holding(
                symbol = "LOSS",
                quantity = 10,
                ltp = 50.0,
                avgPrice = 100.0,
                close = 40.0
            )
        )
        
        val result = useCase(holdings)
        
        assertEquals(-100.0, result.todayPnl, 0.001)
    }
    
    @Test
    fun `multiple holdings aggregates correctly`() {
        val holdings = listOf(
            Holding(
                symbol = "STOCK1",
                quantity = 100,
                ltp = 50.0,
                avgPrice = 40.0,
                close = 55.0
            ),
            Holding(
                symbol = "STOCK2",
                quantity = 200,
                ltp = 25.0,
                avgPrice = 30.0,
                close = 20.0
            )
        )
        
        val result = useCase(holdings)
        
        // Current Value = (50*100) + (25*200) = 5000 + 5000 = 10000
        assertEquals(10000.0, result.currentValue, 0.001)
        
        // Total Investment = (40*100) + (30*200) = 4000 + 6000 = 10000
        assertEquals(10000.0, result.totalInvestment, 0.001)
        
        // Total PNL = 10000 - 10000 = 0
        assertEquals(0.0, result.totalPnl, 0.001)
        
        // Today's PNL = ((55-50)*100) + ((20-25)*200) = 500 + (-1000) = -500
        assertEquals(-500.0, result.todayPnl, 0.001)
    }
    
    @Test
    fun `zero quantity holding contributes zero`() {
        val holdings = listOf(
            Holding(
                symbol = "ZERO",
                quantity = 0,
                ltp = 100.0,
                avgPrice = 90.0,
                close = 110.0
            )
        )
        
        val result = useCase(holdings)
        
        assertEquals(0.0, result.currentValue, 0.001)
        assertEquals(0.0, result.totalInvestment, 0.001)
        assertEquals(0.0, result.totalPnl, 0.001)
        assertEquals(0.0, result.todayPnl, 0.001)
    }
}
