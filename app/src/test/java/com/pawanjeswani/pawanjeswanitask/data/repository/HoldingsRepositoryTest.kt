package com.pawanjeswani.pawanjeswanitask.data.repository

import com.pawanjeswani.pawanjeswanitask.data.remote.HoldingsApiService
import com.pawanjeswani.pawanjeswanitask.data.remote.dto.HoldingData
import com.pawanjeswani.pawanjeswanitask.data.remote.dto.HoldingDto
import com.pawanjeswani.pawanjeswanitask.data.remote.dto.HoldingResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class HoldingsRepositoryTest {

    private lateinit var apiService: HoldingsApiService
    private lateinit var repository: HoldingsRepositoryImpl

    @Before
    fun setup() {
        apiService = mockk()
        repository = HoldingsRepositoryImpl(apiService)
    }

    @Test
    fun `getHoldings returns successfully mapped holdings`() = runTest {
        // Given
        val holdingDto = HoldingDto(
            symbol = "TCS",
            quantity = 10,
            ltp = 3200.0,
            avgPrice = 3000.0,
            close = 3250.0
        )
        val response = HoldingResponse(
            data = HoldingData(
                userHolding = listOf(holdingDto)
            )
        )
        coEvery { apiService.getHoldings() } returns response

        // When
        val result = repository.getHoldings()

        // Then
        assertTrue(result.isSuccess)
        val holdings = result.getOrNull()
        assertEquals(1, holdings?.size)
        assertEquals("TCS", holdings?.first()?.symbol)
    }

    @Test
    fun `getHoldings filters out null holdings`() = runTest {
        // Given
        val validDto = HoldingDto("TCS", 10, 3200.0, 3000.0, 3250.0)
        val invalidDto = HoldingDto(null, 10, 3200.0, 3000.0, 3250.0) // Missing symbol
        val response = HoldingResponse(
            data = HoldingData(
                userHolding = listOf(validDto, invalidDto)
            )
        )
        coEvery { apiService.getHoldings() } returns response

        // When
        val result = repository.getHoldings()

        // Then
        assertTrue(result.isSuccess)
        val holdings = result.getOrNull()
        assertEquals(1, holdings?.size)
        assertEquals("TCS", holdings?.first()?.symbol)
    }

    @Test
    fun `getHoldings returns failure on api exception`() = runTest {
        // Given
        val exception = RuntimeException("Network Error")
        coEvery { apiService.getHoldings() } throws exception

        // When
        val result = repository.getHoldings()

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
