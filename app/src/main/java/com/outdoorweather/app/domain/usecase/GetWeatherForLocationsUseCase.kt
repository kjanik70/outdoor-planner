package com.outdoorweather.app.domain.usecase

import com.outdoorweather.app.domain.model.DaySelection
import com.outdoorweather.app.domain.model.Location
import com.outdoorweather.app.domain.model.LocationWeather
import com.outdoorweather.app.domain.model.WeatherPreferences
import com.outdoorweather.app.domain.repository.WeatherRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetWeatherForLocationsUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val scoreLocations: ScoreLocationsUseCase
) {
    suspend operator fun invoke(
        locations: List<Location>,
        prefs: WeatherPreferences,
        day: DaySelection
    ): List<LocationWeather> = coroutineScope {
        val results = locations.map { location ->
            async {
                val hourly = weatherRepository.getWeather(location)
                val score = scoreLocations(hourly, prefs, day)
                LocationWeather(
                    location = location,
                    hourlyForecasts = hourly,
                    score = score
                )
            }
        }.map { it.await() }

        val sorted = results.sortedByDescending { it.score }
        sorted.mapIndexed { index, lw ->
            lw.copy(isBestPick = index == 0 && sorted.size > 0)
        }
    }
}
