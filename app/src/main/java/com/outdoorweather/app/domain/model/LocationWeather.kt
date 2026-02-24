package com.outdoorweather.app.domain.model

data class LocationWeather(
    val location: Location,
    val hourlyForecasts: List<HourlyWeather>,
    val score: Float,
    val isBestPick: Boolean = false
)
