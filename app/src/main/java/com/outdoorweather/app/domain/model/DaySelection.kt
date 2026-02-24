package com.outdoorweather.app.domain.model

import java.time.DayOfWeek
import java.time.LocalDate

sealed class DaySelection {
    object Today : DaySelection()
    object Tomorrow : DaySelection()
    object Weekend : DaySelection()

    fun toLocalDates(): List<LocalDate> {
        val today = LocalDate.now()
        return when (this) {
            is Today -> listOf(today)
            is Tomorrow -> listOf(today.plusDays(1))
            is Weekend -> {
                val daysUntilSat = (DayOfWeek.SATURDAY.value - today.dayOfWeek.value + 7) % 7
                val sat = today.plusDays(daysUntilSat.toLong().coerceAtLeast(0))
                listOf(sat, sat.plusDays(1))
            }
        }
    }

    fun displayName(): String = when (this) {
        is Today -> "Today"
        is Tomorrow -> "Tomorrow"
        is Weekend -> "Weekend"
    }
}
