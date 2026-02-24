package com.outdoorweather.app.presentation.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.outdoorweather.app.domain.model.Location
import com.outdoorweather.app.domain.usecase.AddLocationUseCase
import com.outdoorweather.app.domain.usecase.DeleteLocationUseCase
import com.outdoorweather.app.domain.usecase.GetSavedLocationsUseCase
import com.outdoorweather.app.domain.usecase.SearchLocationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val getSavedLocations: GetSavedLocationsUseCase,
    private val searchLocations: SearchLocationsUseCase,
    private val addLocation: AddLocationUseCase,
    private val deleteLocation: DeleteLocationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocationsUiState())
    val uiState: StateFlow<LocationsUiState> = _uiState

    private val _searchQuery = MutableStateFlow("")

    init {
        observeSavedLocations()
        observeSearchQuery()
    }

    private fun observeSavedLocations() {
        getSavedLocations().onEach { locations ->
            _uiState.update { it.copy(savedLocations = locations) }
        }.launchIn(viewModelScope)
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        _searchQuery.debounce(300).onEach { query ->
            if (query.isBlank()) {
                _uiState.update { it.copy(searchResults = emptyList(), isSearching = false) }
            } else {
                _uiState.update { it.copy(isSearching = true) }
                try {
                    val results = searchLocations(query)
                    _uiState.update { it.copy(searchResults = results, isSearching = false, error = null) }
                } catch (e: Exception) {
                    _uiState.update { it.copy(isSearching = false, error = "Search failed: ${e.message}") }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        _searchQuery.value = query
    }

    fun onAddLocation(location: Location) {
        viewModelScope.launch {
            try {
                addLocation(location.copy(id = 0))
                _uiState.update { it.copy(searchQuery = "", searchResults = emptyList()) }
                _searchQuery.value = ""
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to add location: ${e.message}") }
            }
        }
    }

    fun onDeleteLocation(location: Location) {
        viewModelScope.launch {
            try {
                deleteLocation(location)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to delete location: ${e.message}") }
            }
        }
    }
}
