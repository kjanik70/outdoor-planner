package com.outdoorweather.app.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Locations : Screen("locations")
    object Settings : Screen("settings")
}
