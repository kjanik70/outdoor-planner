package com.outdoorweather.app.domain.model

data class Location(
    val id: Long = 0,
    val name: String,
    val displayName: String,
    val latitude: Double,
    val longitude: Double,
    val timezone: String
)
