package com.pawanjeswani.pawanjeswanitask.ui.holdings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pawanjeswani.pawanjeswanitask.R
import com.pawanjeswani.pawanjeswanitask.domain.model.Holding
import com.pawanjeswani.pawanjeswanitask.ui.theme.LossRed
import com.pawanjeswani.pawanjeswanitask.ui.theme.PawanJeswaniTaskTheme
import com.pawanjeswani.pawanjeswanitask.ui.theme.ProfitGreen
import com.pawanjeswani.pawanjeswanitask.ui.theme.greyLabel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HoldingItem(
    holding: Holding,
    modifier: Modifier = Modifier
) {
    val currencyFormatter = remember { NumberFormat.getCurrencyInstance(Locale.forLanguageTag("en-IN")) }
    val pnlColor = if (holding.pnl >= 0) ProfitGreen else LossRed
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left Column: Symbol and Quantity
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Text(
                    text = holding.symbol,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.label_net_qty),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 10.sp,
                        color = greyLabel
                    )
                    Text(
                        text = "${holding.quantity}",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Right Column: LTP and P&L
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.label_ltp),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 10.sp,
                        color = greyLabel
                    )
                    Text(
                        text = currencyFormatter.format(holding.ltp),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.label_pnl),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 10.sp,
                        color = greyLabel
                    )
                    Text(
                        text = currencyFormatter.format(holding.pnl),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = pnlColor
                    )
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(top = 12.dp),
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HoldingItemProfitPreview() {
    PawanJeswaniTaskTheme {
        HoldingItem(
            holding = Holding(
                symbol = "TCS",
                quantity = 100,
                ltp = 3500.0,
                avgPrice = 3200.0,
                close = 3550.0
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HoldingItemLossPreview() {
    PawanJeswaniTaskTheme {
        HoldingItem(
            holding = Holding(
                symbol = "INFY",
                quantity = 50,
                ltp = 1400.0,
                avgPrice = 1600.0,
                close = 1380.0
            )
        )
    }
}
