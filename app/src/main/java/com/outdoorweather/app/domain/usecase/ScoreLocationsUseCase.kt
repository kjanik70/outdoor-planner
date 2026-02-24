package com.outdoorweather.app.domain.usecase

import com.outdoorweather.app.domain.model.DaySelection
import com.outdoorweather.app.domain.model.HourlyWeather
import com.outdoorweather.app.domain.model.WeatherPreferences
import javax.inject.Inject
import kotlin.math.abs

class ScoreLocationsUseCase @Inject constructor() {

    operator fun invoke(
        hourly: List<HourlyWeather>,
        prefs: WeatherPreferences,
        day: DaySelection
    ): Float {
        val targetDates = day.toLocalDates()
        val filtered = hourly.filter {
            it.date in targetDates && it.hour in prefs.startHour until prefs.endHour
        }
        if (filtered.isEmpty()) return 0f

        val hourScores = filtered.map { h ->
            val tempScore = maxOf(0f, 100f - abs(h.temperatureF - prefs.idealTempF) * 3.33f)
            val precipScore = (100 - h.precipitationProbability).toFloat()
            val condScore = if (prefs.preferSunshine) sunshineScore(h) else snowScore(h)
            (tempScore + precipScore + condScore) / 3f
        }
        return hourScores.average().toFloat()
    }

    private fun sunshineScore(h: HourlyWeather): Float = when (h.weatherCode) {
        0 -> 100f
        1 -> 85f
        2 -> 65f
        3 -> 40f
        in 51..67 -> 10f
        in 71..77 -> 20f
        85, 86 -> 20f
        in 80..82 -> 15f
        else -> 30f
    }

    private fun snowScore(h: HourlyWeather): Float = when {
        h.snowfall > 0.5f -> 100f
        h.weatherCode in listOf(71, 73, 75, 77, 85, 86) -> 80f
        h.weatherCode in 51..82 -> 20f
        else -> 10f
    }
}
