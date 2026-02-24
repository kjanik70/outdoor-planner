package com.outdoorweather.app.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.outdoorweather.app.domain.model.HourlyWeather
import com.outdoorweather.app.domain.model.WeatherPreferences
import kotlin.math.abs

@Composable
fun HourlyWeatherRow(
    hourlyForecasts: List<HourlyWeather>,
    preferences: WeatherPreferences,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(hourlyForecasts) { hour ->
            HourCell(
                hourlyWeather = hour,
                idealTempF = preferences.idealTempF
            )
        }
    }
}

@Composable
private fun HourCell(
    hourlyWeather: HourlyWeather,
    idealTempF: Float,
    modifier: Modifier = Modifier
) {
    val tempColor = temperatureColor(hourlyWeather.temperatureF, idealTempF)
    val precipColor = if (hourlyWeather.precipitationProbability > 30) Color(0xFF1565C0) else Color.Gray

    Column(
        modifier = modifier.width(52.dp).padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = formatHour(hourlyWeather.hour),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        WeatherConditionIcon(
            weatherCode = hourlyWeather.weatherCode,
            fontSize = 20.sp
        )
        Text(
            text = "${hourlyWeather.temperatureF.toInt()}°",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            color = tempColor
        )
        Text(
            text = "${hourlyWeather.precipitationProbability}%",
            style = MaterialTheme.typography.labelSmall,
            color = precipColor
        )
    }
}

private fun formatHour(hour: Int): String {
    return when {
        hour == 0 -> "12am"
        hour < 12 -> "${hour}am"
        hour == 12 -> "12pm"
        else -> "${hour - 12}pm"
    }
}

private fun temperatureColor(tempF: Float, idealTempF: Float): Color {
    val diff = tempF - idealTempF
    return when {
        diff < -20 -> Color(0xFF1565C0) // Very cold - deep blue
        diff < -10 -> Color(0xFF1976D2) // Cold - blue
        diff < 0 -> Color(0xFF42A5F5)   // Slightly cold - light blue
        diff < 10 -> Color(0xFF4CAF50)  // Near ideal - green
        diff < 20 -> Color(0xFFFFA726)  // Warm - orange
        else -> Color(0xFFEF5350)       // Hot - red
    }
}
