package com.outdoorweather.app.domain.model

data class WeatherPreferences(
    val defaultDay: DaySelection = DaySelection.Tomorrow,
    val startHour: Int = 9,
    val endHour: Int = 15,
    val idealTempF: Float = 80f,
    val preferSunshine: Boolean = true,
    val tempUnitFahrenheit: Boolean = true
)
