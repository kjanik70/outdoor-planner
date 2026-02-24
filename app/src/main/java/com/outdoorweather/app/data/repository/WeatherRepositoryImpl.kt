package com.outdoorweather.app.data.repository

import com.outdoorweather.app.data.remote.WeatherApiService
import com.outdoorweather.app.domain.model.HourlyWeather
import com.outdoorweather.app.domain.model.Location
import com.outdoorweather.app.domain.repository.WeatherRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService
) : WeatherRepository {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")

    override suspend fun getWeather(location: Location): List<HourlyWeather> {
        val response = weatherApiService.getHourlyForecast(
            latitude = location.latitude,
            longitude = location.longitude
        )
        val hourly = response.hourly
        return hourly.time.indices.map { i ->
            val dateTime = LocalDateTime.parse(hourly.time[i], formatter)
            HourlyWeather(
                hour = dateTime.hour,
                date = LocalDate.of(dateTime.year, dateTime.month, dateTime.dayOfMonth),
                temperatureF = hourly.temperature_2m[i],
                precipitationProbability = hourly.precipitation_probability.getOrNull(i) ?: 0,
                weatherCode = hourly.weather_code[i],
                cloudCover = hourly.cloud_cover.getOrNull(i) ?: 0,
                snowfall = hourly.snowfall.getOrNull(i) ?: 0f
            )
        }
    }
}
