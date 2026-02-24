package com.outdoorweather.app.presentation.home

import com.outdoorweather.app.domain.model.DaySelection
import com.outdoorweather.app.domain.model.LocationWeather
import com.outdoorweather.app.domain.model.WeatherPreferences

sealed class HomeUiState {
    object Loading : HomeUiState()
    object NoLocations : HomeUiState()
    data class Success(
        val locations: List<LocationWeather>,
        val selectedDay: DaySelection,
        val preferences: WeatherPreferences
    ) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}
