package com.eziosoft.parisinnumbers.presentation.ui.mapScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eziosoft.parisinnumbers.domain.MoviesRepository
import com.eziosoft.parisinnumbers.navigation.ActionDispatcher
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.heatmaps.HeatmapTileProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val repository: MoviesRepository,
    private val actionDispatcher: ActionDispatcher
) : ViewModel() {

    private val _screenStateFlow = MutableStateFlow(ScreenState())
    val screenStateFlow = _screenStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            val allMovies = repository.getAllMovies()

            allMovies.onSuccess { movies ->
                movies?.let {
                    val allMarkers = movies.map { Marker(LatLng(it.lat, it.lon), name = it.title) }

                    val heatMapProvider = HeatmapTileProvider.Builder()
                        .data(allMarkers.map { it.position })
                        .build()

                    _screenStateFlow.emit(ScreenState(emptyList(), heatMapProvider))
                }
            }
        }
    }
}
