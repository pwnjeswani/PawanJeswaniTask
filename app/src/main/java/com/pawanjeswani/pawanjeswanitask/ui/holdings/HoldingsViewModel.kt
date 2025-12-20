package com.pawanjeswani.pawanjeswanitask.ui.holdings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pawanjeswani.pawanjeswanitask.domain.usecase.CalculatePortfolioSummaryUseCase
import com.pawanjeswani.pawanjeswanitask.domain.usecase.GetHoldingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HoldingsViewModel @Inject constructor(
    private val getHoldingsUseCase: GetHoldingsUseCase,
    private val calculatePortfolioSummaryUseCase: CalculatePortfolioSummaryUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<HoldingsUiState>(HoldingsUiState.Loading)
    val uiState: StateFlow<HoldingsUiState> = _uiState.asStateFlow()
    
    init {
        loadHoldings()
    }
    
    fun loadHoldings() {
        viewModelScope.launch {
            _uiState.value = HoldingsUiState.Loading
            
            getHoldingsUseCase().fold(
                onSuccess = { holdings ->
                    val summary = calculatePortfolioSummaryUseCase(holdings)
                    _uiState.value = HoldingsUiState.Success(
                        holdings = holdings,
                        summary = summary
                    )
                },
                onFailure = { throwable ->
                    _uiState.value = HoldingsUiState.Error(
                        message = throwable.message ?: "An unexpected error occurred"
                    )
                }
            )
        }
    }
    
    fun retry() {
        loadHoldings()
    }
}
