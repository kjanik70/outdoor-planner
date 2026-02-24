package com.outdoorweather.app.domain.usecase

import com.outdoorweather.app.domain.model.Location
import com.outdoorweather.app.domain.repository.LocationRepository
import javax.inject.Inject

class SearchLocationsUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(query: String): List<Location> {
        if (query.isBlank()) return emptyList()
        return locationRepository.searchLocations(query)
    }
}
