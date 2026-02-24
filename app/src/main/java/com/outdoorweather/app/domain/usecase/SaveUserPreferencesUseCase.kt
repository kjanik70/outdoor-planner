package com.outdoorweather.app.domain.usecase

import com.outdoorweather.app.data.preferences.UserPreferencesDataStore
import com.outdoorweather.app.domain.model.WeatherPreferences
import javax.inject.Inject

class SaveUserPreferencesUseCase @Inject constructor(
    private val dataStore: UserPreferencesDataStore
) {
    suspend operator fun invoke(preferences: WeatherPreferences) {
        dataStore.update(preferences)
    }
}
