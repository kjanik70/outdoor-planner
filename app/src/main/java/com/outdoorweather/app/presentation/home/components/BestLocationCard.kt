package com.outdoorweather.app.presentation.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.outdoorweather.app.domain.model.DaySelection
import com.outdoorweather.app.domain.model.LocationWeather
import com.outdoorweather.app.domain.model.WeatherPreferences

private val GoldAmber = Color(0xFFFFC107)

@Composable
fun BestLocationCard(
    locationWeather: LocationWeather,
    preferences: WeatherPreferences,
    selectedDay: DaySelection,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(locationWeather.location.id) { visible = true }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInVertically(initialOffsetY = { -40 }),
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .border(2.dp, GoldAmber, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("⭐", style = MaterialTheme.typography.titleLarge)
                        Column {
                            Text(
                                text = locationWeather.location.displayName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "BEST PICK",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = GoldAmber
                            )
                        }
                    }
                    ScoreBadge(score = locationWeather.score, isPrimary = true)
                }

                val targetDates = selectedDay.toLocalDates()
                val filteredHours = locationWeather.hourlyForecasts.filter {
                    it.date in targetDates &&
                            it.hour in preferences.startHour until preferences.endHour
                }

                if (filteredHours.isNotEmpty()) {
                    val avgTemp = filteredHours.map { it.temperatureF }.average().toFloat()
                    val avgPrecip = filteredHours.map { it.precipitationProbability }.average().toInt()

                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Avg: ${avgTemp.toInt()}°F",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Rain: $avgPrecip%",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (avgPrecip > 30) Color(0xFF1565C0)
                            else MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    HourlyWeatherRow(
                        hourlyForecasts = filteredHours,
                        preferences = preferences,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ScoreBadge(score: Float, isPrimary: Boolean = false, modifier: Modifier = Modifier) {
    val bgColor = if (isPrimary) GoldAmber else MaterialTheme.colorScheme.secondaryContainer
    val textColor = if (isPrimary) Color(0xFF1A1A1A) else MaterialTheme.colorScheme.onSecondaryContainer

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = bgColor),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "${score.toInt()}",
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}
