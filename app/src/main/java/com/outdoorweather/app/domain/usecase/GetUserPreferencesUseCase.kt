package com.outdoorweather.app.domain.usecase

import com.outdoorweather.app.data.preferences.UserPreferencesDataStore
import com.outdoorweather.app.domain.model.WeatherPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserPreferencesUseCase @Inject constructor(
    private val dataStore: UserPreferencesDataStore
) {
    operator fun invoke(): Flow<WeatherPreferences> = dataStore.preferences
}
