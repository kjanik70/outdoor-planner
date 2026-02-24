package com.outdoorweather.app.domain.repository

import com.outdoorweather.app.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getSavedLocations(): Flow<List<Location>>
    suspend fun addLocation(location: Location)
    suspend fun deleteLocation(location: Location)
    suspend fun searchLocations(query: String): List<Location>
}
