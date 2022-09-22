package com.eziosoft.parisinnumbers.presentation.ui.mapScreen

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eziosoft.parisinnumbers.domain.OpenApiRepository
import com.eziosoft.parisinnumbers.navigation.ActionDispatcher
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.heatmaps.HeatmapTileProvider
import kotlinx.coroutines.launch

data class Marker(
    val position: LatLng,
    val name: String
)

data class ScreenState(
    val markerList: List<Marker> = emptyList(),
    val heatmapTileProvider: HeatmapTileProvider? = null
)

class MapScreenViewModel(
    private val repository: OpenApiRepository,
    private val actionDispatcher: ActionDispatcher
) : ViewModel() {

    var screenState by mutableStateOf(ScreenState())
        private set

    init {
        viewModelScope.launch {
            val allMovies = repository.getAllMovies()

            allMovies.onSuccess { movies ->
                movies?.let {
                    val allMarkers = movies.map { Marker(LatLng(it.lat, it.lon), name = it.title) }

                    val heatMapProvider = HeatmapTileProvider.Builder()
                        .data(allMarkers.map { it.position })
                        .build()

                    screenState = screenState.copy(
                        markerList = allMarkers,
                        heatmapTileProvider = heatMapProvider
                    )
                }
            }
        }
    }
}
