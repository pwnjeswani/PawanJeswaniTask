package com.pawanjeswani.pawanjeswanitask.ui.holdings

import com.pawanjeswani.pawanjeswanitask.domain.model.Holding
import com.pawanjeswani.pawanjeswanitask.domain.model.PortfolioSummary
import com.pawanjeswani.pawanjeswanitask.domain.usecase.CalculatePortfolioSummaryUseCase
import com.pawanjeswani.pawanjeswanitask.domain.usecase.GetHoldingsUseCase
import com.pawanjeswani.pawanjeswanitask.util.UiText
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HoldingsViewModelTest {
    
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getHoldingsUseCase: GetHoldingsUseCase
    private lateinit var calculatePortfolioSummaryUseCase: CalculatePortfolioSummaryUseCase
    // Context no longer needed
    private lateinit var viewModel: HoldingsViewModel
    
    // ... (sample data setup remains same) ...

    val sampleHoldings = listOf(
        Holding(symbol = "TCS", quantity = 100, ltp = 3500.0, avgPrice = 3200.0, close = 3550.0),
        Holding(symbol = "INFY", quantity = 50, ltp = 1400.0, avgPrice = 1600.0, close = 1380.0),
        Holding(symbol = "RELIANCE", quantity = 25, ltp = 2400.0, avgPrice = 2200.0, close = 2450.0)
    )

    private val sampleSummary = PortfolioSummary(
        currentValue = 480000.0,
        totalInvestment = 455000.0,
        totalPnl = 25000.0,
        todayPnl = 5000.0
    )
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getHoldingsUseCase = mockk()
        calculatePortfolioSummaryUseCase = mockk()
        // No context setup needed
    }
    
    // ... (tearDown remains same) ...

    @Test
    fun `initial state is loading`() = runTest {
        coEvery { getHoldingsUseCase() } returns Result.success(sampleHoldings)
        every { calculatePortfolioSummaryUseCase(any()) } returns sampleSummary
        
        viewModel = HoldingsViewModel(getHoldingsUseCase, calculatePortfolioSummaryUseCase)
        
        assertTrue(viewModel.uiState.value is HoldingsUiState.Loading)
    }

    @Test
    fun `success state when data loaded`() = runTest {
        coEvery { getHoldingsUseCase() } returns Result.success(sampleHoldings)
        every { calculatePortfolioSummaryUseCase(any()) } returns sampleSummary
        
        viewModel = HoldingsViewModel(getHoldingsUseCase, calculatePortfolioSummaryUseCase)
        advanceUntilIdle()
        
        val state = viewModel.uiState.value
        assertTrue(state is HoldingsUiState.Success)
        assertEquals(sampleHoldings, (state as HoldingsUiState.Success).holdings)
        assertEquals(sampleSummary, state.summary)
    }

    @Test
    fun `error state when network fails`() = runTest {
        val errorMessage = "Network error"
        coEvery { getHoldingsUseCase() } returns Result.failure(Exception(errorMessage))
        
        viewModel = HoldingsViewModel(getHoldingsUseCase, calculatePortfolioSummaryUseCase)
        advanceUntilIdle()
        
        val state = viewModel.uiState.value
        assertTrue(state is HoldingsUiState.Error)
        val uiText = (state as HoldingsUiState.Error).message
        assertTrue(uiText is UiText.StringResource)
        assertEquals(com.pawanjeswani.pawanjeswanitask.R.string.error_generic, (uiText as UiText.StringResource).resId)
    }

    @Test
    fun `error state shows specific message for timeout`() = runTest {
        coEvery { getHoldingsUseCase() } returns Result.failure(java.net.SocketTimeoutException())
        
        viewModel = HoldingsViewModel(getHoldingsUseCase, calculatePortfolioSummaryUseCase)
        advanceUntilIdle()
        
        val state = viewModel.uiState.value
        assertTrue(state is HoldingsUiState.Error)
        val uiText = (state as HoldingsUiState.Error).message
        assertTrue(uiText is UiText.StringResource)
        assertEquals(com.pawanjeswani.pawanjeswanitask.R.string.error_request_timed_out, (uiText as UiText.StringResource).resId)
    }

    @Test
    fun `error state shows generic message for unknown host exception`() = runTest {
        coEvery { getHoldingsUseCase() } returns Result.failure(java.net.UnknownHostException())
        
        viewModel = HoldingsViewModel(getHoldingsUseCase, calculatePortfolioSummaryUseCase)
        advanceUntilIdle()
        
        val state = viewModel.uiState.value
        assertTrue(state is HoldingsUiState.Error)
        val uiText = (state as HoldingsUiState.Error).message
        assertTrue(uiText is UiText.StringResource)
        assertEquals(com.pawanjeswani.pawanjeswanitask.R.string.error_network_connection, (uiText as UiText.StringResource).resId)
    }
    
    @Test
    fun `retry reloads data`() = runTest {
        // First call fails
        coEvery { getHoldingsUseCase() } returns Result.failure(Exception("Error"))
        
        viewModel = HoldingsViewModel(getHoldingsUseCase, calculatePortfolioSummaryUseCase)
        advanceUntilIdle()
        
        assertTrue(viewModel.uiState.value is HoldingsUiState.Error)
        
        // Setup success for retry
        coEvery { getHoldingsUseCase() } returns Result.success(sampleHoldings)
        every { calculatePortfolioSummaryUseCase(any()) } returns sampleSummary
        
        viewModel.retry()
        advanceUntilIdle()
        
        assertTrue(viewModel.uiState.value is HoldingsUiState.Success)
    }
    
    @Test
    fun `empty holdings list returns success with empty list`() = runTest {
        coEvery { getHoldingsUseCase() } returns Result.success(emptyList())
        every { calculatePortfolioSummaryUseCase(any()) } returns PortfolioSummary(0.0, 0.0, 0.0, 0.0)
        
        viewModel = HoldingsViewModel(getHoldingsUseCase, calculatePortfolioSummaryUseCase)
        advanceUntilIdle()
        
        val state = viewModel.uiState.value
        assertTrue(state is HoldingsUiState.Success)
        assertTrue((state as HoldingsUiState.Success).holdings.isEmpty())
    }
}
