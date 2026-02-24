package com.outdoorweather.app.domain.model

import java.time.LocalDate

data class HourlyWeather(
    val hour: Int,
    val date: LocalDate,
    val temperatureF: Float,
    val precipitationProbability: Int,
    val weatherCode: Int,
    val cloudCover: Int,
    val snowfall: Float
)
