package com.outdoorweather.app.presentation.home.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

fun weatherCodeToEmoji(weatherCode: Int): String = when (weatherCode) {
    0 -> "☀️"
    1 -> "🌤️"
    2 -> "⛅"
    3 -> "☁️"
    in 45..48 -> "🌫️"
    in 51..55 -> "🌦️"
    in 56..57 -> "🌧️"
    in 61..65 -> "🌧️"
    in 66..67 -> "🌨️"
    in 71..75 -> "❄️"
    77 -> "❄️"
    in 80..82 -> "🌦️"
    in 85..86 -> "🌨️"
    in 95..99 -> "⛈️"
    else -> "🌡️"
}

fun weatherCodeToDescription(weatherCode: Int): String = when (weatherCode) {
    0 -> "Clear"
    1 -> "Mainly clear"
    2 -> "Partly cloudy"
    3 -> "Overcast"
    in 45..48 -> "Foggy"
    in 51..55 -> "Drizzle"
    in 56..57 -> "Freezing drizzle"
    in 61..65 -> "Rain"
    in 66..67 -> "Freezing rain"
    in 71..75 -> "Snow"
    77 -> "Snow grains"
    in 80..82 -> "Rain showers"
    in 85..86 -> "Snow showers"
    in 95..99 -> "Thunderstorm"
    else -> "Unknown"
}

@Composable
fun WeatherConditionIcon(
    weatherCode: Int,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 24.sp
) {
    Text(
        text = weatherCodeToEmoji(weatherCode),
        fontSize = fontSize,
        modifier = modifier
    )
}
