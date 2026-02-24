package com.outdoorweather.app.domain.usecase

import com.outdoorweather.app.domain.model.Location
import com.outdoorweather.app.domain.repository.LocationRepository
import javax.inject.Inject

class DeleteLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(location: Location) {
        locationRepository.deleteLocation(location)
    }
}
