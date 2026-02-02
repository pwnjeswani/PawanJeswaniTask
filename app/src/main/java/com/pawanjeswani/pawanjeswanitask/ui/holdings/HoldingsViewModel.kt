package com.pawanjeswani.pawanjeswanitask.ui.holdings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pawanjeswani.pawanjeswanitask.R
import com.pawanjeswani.pawanjeswanitask.domain.usecase.CalculatePortfolioSummaryUseCase
import com.pawanjeswani.pawanjeswanitask.domain.usecase.GetHoldingsUseCase
import com.pawanjeswani.pawanjeswanitask.ui.theme.LossRed
import com.pawanjeswani.pawanjeswanitask.ui.theme.ProfitGreen
import com.pawanjeswani.pawanjeswanitask.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel managing holdings data and UI state for the holdings screen
@HiltViewModel
class HoldingsViewModel @Inject constructor(
    private val getHoldingsUseCase: GetHoldingsUseCase,
    private val calculatePortfolioSummaryUseCase: CalculatePortfolioSummaryUseCase
) : ViewModel() {
    
    // StateFlow holding the current UI state (Loading, Success, or Error)
    private val _uiState = MutableStateFlow<HoldingsUiState>(HoldingsUiState.Loading)
    val uiState: StateFlow<HoldingsUiState> = _uiState.asStateFlow()
    
    init {
        loadHoldings()
    }
    
    // Loads holdings data and calculates portfolio summary
    fun loadHoldings() {
        viewModelScope.launch {
            _uiState.value = HoldingsUiState.Loading
            
            getHoldingsUseCase().fold(
                onSuccess = { holdings ->
                    val summary = calculatePortfolioSummaryUseCase(holdings)
                    _uiState.value = HoldingsUiState.Success(
                        holdings = holdings,
                        summary = summary,
                        pnLColor =  if(summary.totalPnl >= 0) ProfitGreen else LossRed
                    )
                },
                onFailure = { throwable ->
                    val errorText = when (throwable) {
                        is java.net.SocketTimeoutException -> UiText.StringResource(R.string.error_request_timed_out)
                        is java.net.UnknownHostException -> UiText.StringResource(R.string.error_network_connection)
                        else -> UiText.StringResource(R.string.error_generic)
                    }
                    _uiState.value = HoldingsUiState.Error(message = errorText)
                }
            )
        }
    }
    
    // Retries loading holdings after an error
    fun retry() {
        loadHoldings()
    }
}
