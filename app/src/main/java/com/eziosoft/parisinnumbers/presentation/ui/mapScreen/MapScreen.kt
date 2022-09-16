package com.eziosoft.parisinnumbers.presentation.ui.mapScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.TileProvider
import com.google.maps.android.compose.*
import com.google.maps.android.heatmaps.HeatmapTileProvider
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.getViewModel

private val PARIS_POSITION = LatLng(48.8566, 2.3522)

@Composable
fun MapScreen() {
    val viewModel: MapScreenViewModel = getViewModel()
    val cameraPositionState = rememberCameraPositionState() {
        position = CameraPosition.fromLatLngZoom(PARIS_POSITION, 11f)
    }
    val uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }

    var markers by remember {
        mutableStateOf(listOf<MarkerState>())
    }

    var heatmapTileProvider: HeatmapTileProvider? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = true) {
        viewModel.screenStateFlow.collectLatest() { screenState ->
            markers = screenState.markerList.map { MarkerState(it.position) }

            screenState.heatmapTileProvider?.let {
                heatmapTileProvider = it
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            uiSettings = uiSettings,
            properties = properties,
            cameraPositionState = cameraPositionState
        ) {
            markers.forEach() {
                Marker(state = it)
            }

            heatmapTileProvider?.let {
                TileOverlay(tileProvider = heatmapTileProvider as TileProvider)
            }
        }
    }
}
