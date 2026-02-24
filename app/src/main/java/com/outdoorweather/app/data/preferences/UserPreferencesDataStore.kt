package com.outdoorweather.app.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.outdoorweather.app.domain.model.DaySelection
import com.outdoorweather.app.domain.model.WeatherPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreferencesDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val DEFAULT_DAY = stringPreferencesKey("default_day")
        val START_HOUR = intPreferencesKey("start_hour")
        val END_HOUR = intPreferencesKey("end_hour")
        val IDEAL_TEMP_F = floatPreferencesKey("ideal_temp_f")
        val PREFER_SUNSHINE = booleanPreferencesKey("prefer_sunshine")
        val TEMP_UNIT_FAHRENHEIT = booleanPreferencesKey("temp_unit_fahrenheit")
    }

    val preferences: Flow<WeatherPreferences> = context.dataStore.data.map { prefs ->
        WeatherPreferences(
            defaultDay = when (prefs[Keys.DEFAULT_DAY]) {
                "Today" -> DaySelection.Today
                "Weekend" -> DaySelection.Weekend
                else -> DaySelection.Tomorrow
            },
            startHour = prefs[Keys.START_HOUR] ?: 9,
            endHour = prefs[Keys.END_HOUR] ?: 15,
            idealTempF = prefs[Keys.IDEAL_TEMP_F] ?: 80f,
            preferSunshine = prefs[Keys.PREFER_SUNSHINE] ?: true,
            tempUnitFahrenheit = prefs[Keys.TEMP_UNIT_FAHRENHEIT] ?: true
        )
    }

    suspend fun update(weatherPreferences: WeatherPreferences) {
        context.dataStore.edit { prefs ->
            prefs[Keys.DEFAULT_DAY] = weatherPreferences.defaultDay.displayName()
            prefs[Keys.START_HOUR] = weatherPreferences.startHour
            prefs[Keys.END_HOUR] = weatherPreferences.endHour
            prefs[Keys.IDEAL_TEMP_F] = weatherPreferences.idealTempF
            prefs[Keys.PREFER_SUNSHINE] = weatherPreferences.preferSunshine
            prefs[Keys.TEMP_UNIT_FAHRENHEIT] = weatherPreferences.tempUnitFahrenheit
        }
    }
}
