package com.outdoorweather.app.presentation.settings

import com.outdoorweather.app.domain.model.WeatherPreferences

data class SettingsUiState(
    val preferences: WeatherPreferences = WeatherPreferences(),
    val isSaved: Boolean = false
)
