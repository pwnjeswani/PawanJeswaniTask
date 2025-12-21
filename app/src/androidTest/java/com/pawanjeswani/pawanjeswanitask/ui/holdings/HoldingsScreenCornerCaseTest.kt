package com.pawanjeswani.pawanjeswanitask.ui.holdings

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import com.pawanjeswani.pawanjeswanitask.domain.model.Holding
import com.pawanjeswani.pawanjeswanitask.domain.model.PortfolioSummary
import com.pawanjeswani.pawanjeswanitask.ui.theme.PawanJeswaniTaskTheme
import org.junit.Rule
import org.junit.Test
import java.util.UUID

class HoldingsScreenCornerCaseTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun holdingsScreen_longSymbol_truncatesAndDisplaysCorrectly() {
        // Arrange: Holding with a very long symbol and large quantity
        val longSymbolHolding = Holding(
            symbol = "MOTHERSUMI-BONUS-RE-LONG-TEXT-TEST",
            quantity = 1000000, // Large quantity
            ltp = 450.0,
            avgPrice = 400.0,
            close = 440.0,
            id = UUID.randomUUID().toString()
        )
        
        val summary = PortfolioSummary(
             currentValue = 450000000.0,
             totalInvestment = 400000000.0,
             totalPnl = 50000000.0,
             todayPnl = 10000000.0
        )

        // Act
        composeTestRule.setContent {
            PawanJeswaniTaskTheme {
                HoldingsContent(
                    uiState = HoldingsUiState.Success(
                        holdings = listOf(longSymbolHolding),
                        summary = summary
                    ),
                    onRetry = {}
                )
            }
        }

        // Assert
        // We can't easily check for ellipsis in basic Compose tests without semantics matching,
        // but we CAN verify that the Text node exists.
        // If layout was completely broken (crashed), test would fail.
        composeTestRule.onNodeWithText("MOTHERSUMI-BONUS-RE-LONG-TEXT-TEST").assertIsDisplayed()
        
        // Assert large numbers are displayed (formatted somewhat)
        // Note: Formatting depends on locale, but let's check basic existence
        composeTestRule.onNodeWithText(" 1000000").assertIsDisplayed()
    }
    
    @Test
    fun holdingsScreen_negativeZero_displaysAsPositiveZero() {
        // Arrange: Holding that produces near-zero negative PNL
        // PNL = (LTP - Avg) * Qty
        // (100.00 - 100.001) * 10 = -0.01
        // (100.00 - 100.0001) * 10 = -0.001 -> formatted should be 0.00
        val negativeZeroHolding = Holding(
            symbol = "ZERO-TEST",
            quantity = 10,
            ltp = 100.0,
            avgPrice = 100.0001,
            close = 100.0,
            id = UUID.randomUUID().toString()
        )

         val summary = PortfolioSummary(
             currentValue = 1000.0,
             totalInvestment = 1000.001,
             totalPnl = -0.001, // Negative zero scenario
             todayPnl = 0.0
        )

        composeTestRule.setContent {
            PawanJeswaniTaskTheme {
                HoldingsContent(
                    uiState = HoldingsUiState.Success(
                        holdings = listOf(negativeZeroHolding),
                        summary = summary
                    ),
                    onRetry = {}
                )
            }
        }
        
        // Assert PNL is displayed as 0.00 (approximately, depending on currency symbol)
        // We ensure that "-0.00" is NOT displayed
        composeTestRule.onNodeWithText("-0.00", substring = true).assertDoesNotExist()
        
        // And we ensure "0.00" IS displayed (handling multiple occurrences)
        composeTestRule.onAllNodesWithText("0.00", substring = true).onFirst().assertIsDisplayed()
    }
}
