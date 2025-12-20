package com.pawanjeswani.pawanjeswanitask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pawanjeswani.pawanjeswanitask.ui.MainScreen
import com.pawanjeswani.pawanjeswanitask.ui.theme.PawanJeswaniTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PawanJeswaniTaskTheme {
                MainScreen()
            }
        }
    }
}