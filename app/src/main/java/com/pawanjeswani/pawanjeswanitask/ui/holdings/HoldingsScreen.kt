package com.pawanjeswani.pawanjeswanitask.ui.holdings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.pawanjeswani.pawanjeswanitask.R
import com.pawanjeswani.pawanjeswanitask.domain.model.Holding
import com.pawanjeswani.pawanjeswanitask.domain.model.PortfolioSummary
import com.pawanjeswani.pawanjeswanitask.ui.holdings.components.HoldingItem
import com.pawanjeswani.pawanjeswanitask.ui.holdings.components.PortfolioSummarySheet
import com.pawanjeswani.pawanjeswanitask.ui.theme.PawanJeswaniTaskTheme

@Composable
fun HoldingsScreen(
    modifier: Modifier = Modifier,
    viewModel: HoldingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    
    HoldingsContent(
        uiState = uiState,
        onRetry = { viewModel.retry() },
        modifier = modifier
    )
}

@Composable
private fun HoldingsContent(
    uiState: HoldingsUiState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            when (val state = uiState) {
                is HoldingsUiState.Success -> {
                    PortfolioSummarySheet(
                        summary = state.summary,
                        modifier = Modifier.padding(horizontal = 0.dp)
                    )
                }
                else -> { /* No bottom bar for loading/error states */ }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is HoldingsUiState.Loading -> {
                    LoadingContent()
                }
                
                is HoldingsUiState.Success -> {
                    if (state.holdings.isEmpty()) {
                        EmptyContent()
                    } else {
                        HoldingsList(
                            holdings = state.holdings
                        )
                    }
                }
                
                is HoldingsUiState.Error -> {
                    ErrorContent(
                        message = state.message,
                        onRetry = onRetry
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
private fun EmptyContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.msg_no_holdings),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.msg_portfolio_empty),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun HoldingsList(
    holdings: List<Holding>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            items = holdings,
            key = { it.symbol }
        ) { holding ->
            HoldingItem(holding = holding)
        }
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = stringResource(R.string.msg_error_generic),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onRetry) {
                Text(stringResource(R.string.action_retry))
            }
        }
    }
}

// Sample data for previews
private val sampleHoldings = listOf(
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

@Preview(showBackground = true)
@Composable
private fun HoldingsScreenLoadingPreview() {
    PawanJeswaniTaskTheme {
        HoldingsContent(
            uiState = HoldingsUiState.Loading,
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HoldingsScreenSuccessPreview() {
    PawanJeswaniTaskTheme {
        HoldingsContent(
            uiState = HoldingsUiState.Success(
                holdings = sampleHoldings,
                summary = sampleSummary
            ),
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HoldingsScreenEmptyPreview() {
    PawanJeswaniTaskTheme {
        HoldingsContent(
            uiState = HoldingsUiState.Success(
                holdings = emptyList(),
                summary = PortfolioSummary(0.0, 0.0, 0.0, 0.0)
            ),
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HoldingsScreenErrorPreview() {
    PawanJeswaniTaskTheme {
        HoldingsContent(
            uiState = HoldingsUiState.Error("Network connection failed. Please check your internet connection."),
            onRetry = {}
        )
    }
}
