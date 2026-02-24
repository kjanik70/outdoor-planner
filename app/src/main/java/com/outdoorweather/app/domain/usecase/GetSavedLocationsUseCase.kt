package com.outdoorweather.app.domain.usecase

import com.outdoorweather.app.domain.model.Location
import com.outdoorweather.app.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedLocationsUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Flow<List<Location>> = locationRepository.getSavedLocations()
}
