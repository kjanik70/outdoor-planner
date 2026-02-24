package com.outdoorweather.app.presentation.locations

import com.outdoorweather.app.domain.model.Location

data class LocationsUiState(
    val savedLocations: List<Location> = emptyList(),
    val searchQuery: String = "",
    val searchResults: List<Location> = emptyList(),
    val isSearching: Boolean = false,
    val error: String? = null
)
