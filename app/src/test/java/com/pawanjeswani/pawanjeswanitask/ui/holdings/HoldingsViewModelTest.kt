package com.pawanjeswani.pawanjeswanitask.ui.holdings

import android.content.Context
import com.pawanjeswani.pawanjeswanitask.domain.model.Holding
import com.pawanjeswani.pawanjeswanitask.domain.model.PortfolioSummary
import com.pawanjeswani.pawanjeswanitask.domain.usecase.CalculatePortfolioSummaryUseCase
import com.pawanjeswani.pawanjeswanitask.domain.usecase.GetHoldingsUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HoldingsViewModelTest {
    
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getHoldingsUseCase: GetHoldingsUseCase
    private lateinit var calculatePortfolioSummaryUseCase: CalculatePortfolioSummaryUseCase
    private lateinit var context: Context
    private lateinit var viewModel: HoldingsViewModel
    
    private val sampleHoldings = listOf(
        Holding(
            symbol = "TEST1",
            quantity = 100,
            ltp = 50.0,
            avgPrice = 40.0,
            close = 55.0
        ),
        Holding(
            symbol = "TEST2",
            quantity = 200,
            ltp = 25.0,
            avgPrice = 30.0,
            close = 20.0
        )
    )
    
    private val sampleSummary = PortfolioSummary(
        currentValue = 10000.0,
        totalInvestment = 10000.0,
        totalPnl = 0.0,
        todayPnl = -500.0
    )
    
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getHoldingsUseCase = mockk()
        calculatePortfolioSummaryUseCase = mockk()
        context = mockk(relaxed = true)
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    
    @Test
    fun `initial state is loading`() = runTest {
        coEvery { getHoldingsUseCase() } returns Result.success(sampleHoldings)
        every { calculatePortfolioSummaryUseCase(any()) } returns sampleSummary
        
        viewModel = HoldingsViewModel(getHoldingsUseCase, calculatePortfolioSummaryUseCase, context)
        
        // Before advancing, should be loading
        assertTrue(viewModel.uiState.value is HoldingsUiState.Loading)
    }
    
    @Test
    fun `success state when data loaded`() = runTest {
        coEvery { getHoldingsUseCase() } returns Result.success(sampleHoldings)
        every { calculatePortfolioSummaryUseCase(any()) } returns sampleSummary
        
        viewModel = HoldingsViewModel(getHoldingsUseCase, calculatePortfolioSummaryUseCase, context)
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
        
        viewModel = HoldingsViewModel(getHoldingsUseCase, calculatePortfolioSummaryUseCase, context)
        advanceUntilIdle()
        
        val state = viewModel.uiState.value
        assertTrue(state is HoldingsUiState.Error)
        assertEquals(errorMessage, (state as HoldingsUiState.Error).message)
    }
    
    @Test
    fun `retry reloads data`() = runTest {
        // First call fails
        coEvery { getHoldingsUseCase() } returns Result.failure(Exception("Error"))
        
        viewModel = HoldingsViewModel(getHoldingsUseCase, calculatePortfolioSummaryUseCase, context)
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
        
        viewModel = HoldingsViewModel(getHoldingsUseCase, calculatePortfolioSummaryUseCase, context)
        advanceUntilIdle()
        
        val state = viewModel.uiState.value
        assertTrue(state is HoldingsUiState.Success)
        assertTrue((state as HoldingsUiState.Success).holdings.isEmpty())
    }
}
