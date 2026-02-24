package com.outdoorweather.app.data.repository

import com.outdoorweather.app.data.local.LocationDao
import com.outdoorweather.app.data.local.LocationEntity
import com.outdoorweather.app.data.remote.GeocodingApiService
import com.outdoorweather.app.domain.model.Location
import com.outdoorweather.app.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
    private val geocodingApiService: GeocodingApiService
) : LocationRepository {

    override fun getSavedLocations(): Flow<List<Location>> =
        locationDao.getAll().map { entities -> entities.map { it.toDomain() } }

    override suspend fun addLocation(location: Location) {
        locationDao.insert(LocationEntity.fromDomain(location))
    }

    override suspend fun deleteLocation(location: Location) {
        locationDao.delete(LocationEntity.fromDomain(location))
    }

    override suspend fun searchLocations(query: String): List<Location> {
        val response = geocodingApiService.searchLocations(query)
        return response.results?.map { result ->
            val displayName = buildString {
                append(result.name)
                result.admin1?.let { append(", $it") }
                result.country?.let { append(", $it") }
            }
            Location(
                id = result.id,
                name = result.name,
                displayName = displayName,
                latitude = result.latitude,
                longitude = result.longitude,
                timezone = result.timezone ?: "UTC"
            )
        } ?: emptyList()
    }
}
