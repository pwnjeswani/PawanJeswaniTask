package com.pawanjeswani.pawanjeswanitask.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.TrendingUp
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
import com.pawanjeswani.pawanjeswanitask.ui.navigation.FundsScreen
import com.pawanjeswani.pawanjeswanitask.ui.navigation.InvestScreen
import com.pawanjeswani.pawanjeswanitask.ui.navigation.OrdersScreen
import com.pawanjeswani.pawanjeswanitask.ui.navigation.WatchlistScreen
import com.pawanjeswani.pawanjeswanitask.ui.portfolio.PortfolioScreen

data class BottomNavItem(
    val title: String,
    val icon: ImageVector
)

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navItems = listOf(
        BottomNavItem("Watchlists", Icons.Default.List),
        BottomNavItem("Orders", Icons.Default.ShoppingCart),
        BottomNavItem("Portfolio", Icons.Default.Star),
        BottomNavItem("Funds", Icons.Outlined.AccountBalanceWallet),
        BottomNavItem("Invest", Icons.Outlined.TrendingUp)
    )
    
    // Start with Portfolio tab (index 2 - middle)
    var selectedIndex by rememberSaveable { mutableIntStateOf(2) }
    
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
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
        when (selectedIndex) {
            0 -> WatchlistScreen(modifier = Modifier.padding(paddingValues))
            1 -> OrdersScreen(modifier = Modifier.padding(paddingValues))
            2 -> PortfolioScreen(modifier = Modifier.padding(paddingValues))
            3 -> FundsScreen(modifier = Modifier.padding(paddingValues))
            4 -> InvestScreen(modifier = Modifier.padding(paddingValues))
        }
    }
}
