package com.pawanjeswani.pawanjeswanitask.ui.holdings.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pawanjeswani.pawanjeswanitask.R
import com.pawanjeswani.pawanjeswanitask.domain.model.PortfolioSummary
import com.pawanjeswani.pawanjeswanitask.ui.theme.LossRed
import com.pawanjeswani.pawanjeswanitask.ui.theme.ProfitGreen
import com.pawanjeswani.pawanjeswanitask.util.Formatters.formatCurrency
import com.pawanjeswani.pawanjeswanitask.util.Formatters.formatPercentage

// Expandable bottom sheet displaying portfolio summary with P&L details
@Composable
fun PortfolioSummarySheet(
    summary: PortfolioSummary,
    totalPnlColor: Color,
    modifier: Modifier = Modifier
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) } // Tracks expansion state of the sheet
    // val currencyFormatter = remember { NumberFormat.getCurrencyInstance(Locale.forLanguageTag("en-IN")) } // Optimization: Shared formatter used

//    val totalPnlColor = if (summary.totalPnl >= 0) ProfitGreen else LossRed
    val todayPnlColor = if (summary.todayPnl >= 0) ProfitGreen else LossRed

    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (!isExpanded) {
                // Collapsed State: Only show Profit & Loss row with UP arrow
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isExpanded = true },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.label_profit_and_loss),
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = stringResource(R.string.desc_expand),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = "${formatCurrency(summary.totalPnl)} (${formatPercentage(summary.totalPnlPercentage)}%)",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = totalPnlColor
                    )
                }
            } else {
                // Expanded State: Show all details
                
                // Current Value
                SummaryRow(
                    label = stringResource(R.string.label_current_value),
                    value = formatCurrency(summary.currentValue)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Total Investment
                SummaryRow(
                    label = stringResource(R.string.label_total_investment),
                    value = formatCurrency(summary.totalInvestment)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Today's Profit & Loss
                SummaryRow(
                    label = stringResource(R.string.label_todays_profit_and_loss),
                    value = "${formatCurrency(summary.todayPnl)} (${formatPercentage(summary.todayPnlPercentage)}%)",
                    valueColor = todayPnlColor
                )

                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.outline
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Profit & Loss row with DOWN arrow (clickable to collapse)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isExpanded = false },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.label_profit_and_loss),
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = stringResource(R.string.desc_collapse),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = "${formatCurrency(summary.totalPnl)} (${formatPercentage(summary.totalPnlPercentage)}%)",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = totalPnlColor
                    )
                }
            }
        }
    }
}

// Reusable row component for displaying summary label-value pairs
@Composable
private fun SummaryRow(
    label: String,
    value: String,
    valueColor: Color = Color.Unspecified,
    isBold: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 14.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Medium,
            color = if (valueColor != Color.Unspecified) valueColor else MaterialTheme.colorScheme.onSurface
        )
    }
}
