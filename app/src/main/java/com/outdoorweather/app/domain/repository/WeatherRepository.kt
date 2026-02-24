package com.outdoorweather.app.domain.repository

import com.outdoorweather.app.domain.model.HourlyWeather
import com.outdoorweather.app.domain.model.Location

interface WeatherRepository {
    suspend fun getWeather(location: Location): List<HourlyWeather>
}
