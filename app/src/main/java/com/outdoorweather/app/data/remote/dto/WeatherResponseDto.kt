package com.outdoorweather.app.data.remote.dto

data class WeatherResponseDto(
    val hourly: HourlyDataDto,
    val timezone: String
)

data class HourlyDataDto(
    val time: List<String>,
    val temperature_2m: List<Float>,
    val precipitation_probability: List<Int?>,
    val weather_code: List<Int>,
    val cloud_cover: List<Int?>,
    val snowfall: List<Float?>
)
