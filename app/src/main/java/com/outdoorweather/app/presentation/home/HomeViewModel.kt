package com.outdoorweather.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.outdoorweather.app.domain.model.DaySelection
import com.outdoorweather.app.domain.model.Location
import com.outdoorweather.app.domain.model.WeatherPreferences
import com.outdoorweather.app.domain.usecase.GetSavedLocationsUseCase
import com.outdoorweather.app.domain.usecase.GetUserPreferencesUseCase
import com.outdoorweather.app.domain.usecase.GetWeatherForLocationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSavedLocations: GetSavedLocationsUseCase,
    private val getUserPreferences: GetUserPreferencesUseCase,
    private val getWeatherForLocations: GetWeatherForLocationsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    private val _selectedDay = MutableStateFlow<DaySelection>(DaySelection.Tomorrow)

    init {
        observeLocationsAndPreferences()
    }

    private fun observeLocationsAndPreferences() {
        combine(
            getSavedLocations(),
            getUserPreferences(),
            _selectedDay
        ) { locations, prefs, day ->
            Triple(locations, prefs, day)
        }.onEach { (locations, prefs, day) ->
            if (locations.isEmpty()) {
                _uiState.value = HomeUiState.NoLocations
            } else {
                loadWeather(locations, prefs, day)
            }
        }.launchIn(viewModelScope)
    }

    private fun loadWeather(
        locations: List<Location>,
        prefs: WeatherPreferences,
        day: DaySelection
    ) {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val locationWeather = getWeatherForLocations(locations, prefs, day)
                _uiState.value = HomeUiState.Success(
                    locations = locationWeather,
                    selectedDay = day,
                    preferences = prefs
                )
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Failed to load weather")
            }
        }
    }

    fun onDaySelected(day: DaySelection) {
        _selectedDay.value = day
    }

    fun refresh() {
        viewModelScope.launch {
            val locations = getSavedLocations().first()
            val prefs = getUserPreferences().first()
            if (locations.isNotEmpty()) {
                loadWeather(locations, prefs, _selectedDay.value)
            }
        }
    }
}
