package com.eziosoft.parisinnumbers.presentation.ui.mapScreen

import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eziosoft.parisinnumbers.domain.repository.OpenApiRepository
import com.eziosoft.parisinnumbers.presentation.ProjectDispatchers
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.heatmaps.HeatmapTileProvider
import kotlinx.coroutines.launch

data class ScreenState(
    val mapMarkerList: List<MapMarker> = emptyList(),
    val heatmapTileProvider: HeatmapTileProvider? = null
)

class MapScreenViewModel(
    private val repository: OpenApiRepository,
    projectDispatchers: ProjectDispatchers
) : ViewModel() {

    var screenState by mutableStateOf(ScreenState())
        private set

    init {
        viewModelScope.launch(projectDispatchers.ioDispatcher) {
            val allMovies = repository.getAllMovies()

            allMovies.onSuccess { movies ->
                movies?.let {
                    val allMapMarkers = movies.map { MapMarker(LatLng(it.lat, it.lon), name = it.title) }

                    val heatMapProvider = HeatmapTileProvider.Builder()
                        .data(allMapMarkers.map { it.position })
                        .build()

                    screenState = screenState.copy(
                        mapMarkerList = allMapMarkers,
                        heatmapTileProvider = heatMapProvider
                    )
                }
            }
        }
    }
}
