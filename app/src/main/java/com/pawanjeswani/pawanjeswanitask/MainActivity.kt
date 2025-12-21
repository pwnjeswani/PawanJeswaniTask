package com.pawanjeswani.pawanjeswanitask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pawanjeswani.pawanjeswanitask.ui.MainScreen
import com.pawanjeswani.pawanjeswanitask.ui.theme.PawanJeswaniTaskTheme
import dagger.hilt.android.AndroidEntryPoint

// Entry point activity for the app, uses Hilt for dependency injection
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enable edge-to-edge display for immersive experience
        setContent {
            PawanJeswaniTaskTheme {
                MainScreen()
            }
        }
    }
}