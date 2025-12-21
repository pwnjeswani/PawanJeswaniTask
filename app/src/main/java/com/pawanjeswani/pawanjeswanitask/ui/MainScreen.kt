package com.pawanjeswani.pawanjeswanitask.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Rocket
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.pawanjeswani.pawanjeswanitask.R
import com.pawanjeswani.pawanjeswanitask.ui.navigation.FundsScreen
import com.pawanjeswani.pawanjeswanitask.ui.navigation.InvestScreen
import com.pawanjeswani.pawanjeswanitask.ui.navigation.OrdersScreen
import com.pawanjeswani.pawanjeswanitask.ui.navigation.WatchlistScreen
import com.pawanjeswani.pawanjeswanitask.ui.portfolio.PortfolioScreen

// Data class representing bottom navigation item with title and icon
data class BottomNavItem(
    val title: String,
    val icon: ImageVector
)

// Main screen with bottom navigation bar and content switching
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navItems = listOf(
        BottomNavItem(stringResource(R.string.title_watchlist), Icons.Default.Watch),
        BottomNavItem(stringResource(R.string.title_orders), Icons.Default.ShoppingCart),
        BottomNavItem(stringResource(R.string.title_portfolio), Icons.Outlined.ShoppingBag),
        BottomNavItem(stringResource(R.string.title_funds), Icons.Outlined.AccountBalanceWallet),
        BottomNavItem(stringResource(R.string.title_invest), Icons.Outlined.Rocket)
    )
    
    // Start with Portfolio tab (index 2 - middle)
    var selectedIndex by rememberSaveable { mutableIntStateOf(2) }
    
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            // Bottom navigation bar with five tabs
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        label = { Text(text = item.title) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        // Content area showing selected screen based on navigation index
        when (selectedIndex) {
            0 -> WatchlistScreen(modifier = Modifier.padding(paddingValues))
            1 -> OrdersScreen(modifier = Modifier.padding(paddingValues))
            2 -> PortfolioScreen(modifier = Modifier.padding(paddingValues))
            3 -> FundsScreen(modifier = Modifier.padding(paddingValues))
            4 -> InvestScreen(modifier = Modifier.padding(paddingValues))
        }
    }
}
