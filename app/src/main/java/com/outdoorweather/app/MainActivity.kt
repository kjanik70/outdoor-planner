package com.outdoorweather.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.outdoorweather.app.presentation.navigation.AppNavigation
import com.outdoorweather.app.presentation.theme.OutdoorPlannerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OutdoorPlannerTheme {
                AppNavigation()
            }
        }
    }
}
