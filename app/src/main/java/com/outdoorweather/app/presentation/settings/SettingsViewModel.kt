package com.outdoorweather.app.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.outdoorweather.app.domain.model.DaySelection
import com.outdoorweather.app.domain.model.WeatherPreferences
import com.outdoorweather.app.domain.usecase.GetUserPreferencesUseCase
import com.outdoorweather.app.domain.usecase.SaveUserPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getUserPreferences: GetUserPreferencesUseCase,
    private val saveUserPreferences: SaveUserPreferencesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    init {
        getUserPreferences().onEach { prefs ->
            _uiState.update { it.copy(preferences = prefs, isSaved = false) }
        }.launchIn(viewModelScope)
    }

    fun onDefaultDayChanged(day: DaySelection) {
        _uiState.update { it.copy(preferences = it.preferences.copy(defaultDay = day)) }
    }

    fun onStartHourChanged(hour: Int) {
        val endHour = _uiState.value.preferences.endHour
        if (hour < endHour) {
            _uiState.update { it.copy(preferences = it.preferences.copy(startHour = hour)) }
        }
    }

    fun onEndHourChanged(hour: Int) {
        val startHour = _uiState.value.preferences.startHour
        if (hour > startHour) {
            _uiState.update { it.copy(preferences = it.preferences.copy(endHour = hour)) }
        }
    }

    fun onIdealTempChanged(tempF: Float) {
        _uiState.update { it.copy(preferences = it.preferences.copy(idealTempF = tempF)) }
    }

    fun onConditionPreferenceChanged(preferSunshine: Boolean) {
        _uiState.update { it.copy(preferences = it.preferences.copy(preferSunshine = preferSunshine)) }
    }

    fun onSave() {
        viewModelScope.launch {
            saveUserPreferences(_uiState.value.preferences)
            _uiState.update { it.copy(isSaved = true) }
        }
    }
}
