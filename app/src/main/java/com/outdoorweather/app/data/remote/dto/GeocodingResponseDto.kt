package com.outdoorweather.app.data.remote.dto

data class GeocodingResponseDto(
    val results: List<GeoResultDto>?
)

data class GeoResultDto(
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val timezone: String?,
    val country: String?,
    val admin1: String?,
    val country_code: String?
)
