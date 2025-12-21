package com.pawanjeswani.pawanjeswanitask.ui.portfolio

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.pawanjeswani.pawanjeswanitask.ui.holdings.HoldingsScreen
import kotlinx.coroutines.launch

enum class PortfolioTab(val title: String) {
    POSITIONS("POSITIONS"),
    HOLDINGS("HOLDINGS")
}

@Composable
fun PortfolioScreen(modifier: Modifier = Modifier) {
    val tabs = PortfolioTab.entries
    // Start with HOLDINGS tab (index 1)
    val pagerState = rememberPagerState(initialPage = 1) { tabs.size }
    val coroutineScope = rememberCoroutineScope()
    
    Column(modifier = modifier.fillMaxSize()) {
        // Custom Top Bar
        com.pawanjeswani.pawanjeswanitask.ui.portfolio.components.PortfolioTopBar(
            onSortClick = { /* TODO: Implement sort callback */ },
            onSearchClick = { /* TODO: Implement search callback */ },
            onProfileClick = { /* TODO: Implement profile callback */ }
        )

        PrimaryTabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = tab.title,
                            fontWeight = if (pagerState.currentPage == index) 
                                FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }
        
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (tabs[page]) {
                PortfolioTab.POSITIONS -> PositionsScreen()
                PortfolioTab.HOLDINGS -> HoldingsScreen()
            }
        }
    }
}
