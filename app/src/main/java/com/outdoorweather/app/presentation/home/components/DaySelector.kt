package com.outdoorweather.app.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.outdoorweather.app.domain.model.DaySelection

@Composable
fun DaySelector(
    selectedDay: DaySelection,
    onDaySelected: (DaySelection) -> Unit,
    modifier: Modifier = Modifier
) {
    val days = listOf(DaySelection.Today, DaySelection.Tomorrow, DaySelection.Weekend)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        days.forEach { day ->
            FilterChip(
                selected = selectedDay::class == day::class,
                onClick = { onDaySelected(day) },
                label = { Text(day.displayName()) }
            )
        }
    }
}
