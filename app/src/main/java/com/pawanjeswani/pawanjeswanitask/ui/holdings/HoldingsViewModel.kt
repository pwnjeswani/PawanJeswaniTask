package com.pawanjeswani.pawanjeswanitask.ui.holdings

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import androidx.lifecycle.viewModelScope
import com.pawanjeswani.pawanjeswanitask.R
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
    private val calculatePortfolioSummaryUseCase: CalculatePortfolioSummaryUseCase,
    @ApplicationContext private val context: Context
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
                        message = throwable.message ?: context.getString(R.string.error_generic)
                    )
                }
            )
        }
    }
    
    fun retry() {
        loadHoldings()
    }
}
