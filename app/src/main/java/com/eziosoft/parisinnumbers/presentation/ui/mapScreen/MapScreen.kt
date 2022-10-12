package com.eziosoft.parisinnumbers.presentation.ui.mapScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.eziosoft.parisinnumbers.domain.Movie
import com.eziosoft.parisinnumbers.presentation.ui.movieDetailsBottomSheet.MovieDetailsBottomSheet
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.google.maps.android.heatmaps.HeatmapTileProvider
import org.koin.androidx.compose.getViewModel

private val PARIS_POSITION = LatLng(48.8566, 2.3522)

@Composable
fun MapScreen(modifier: Modifier = Modifier.fillMaxSize()) {
    val viewModel: MapScreenViewModel = getViewModel()
    Map(
        viewModel,
        modifier,
        onBoundsChange = { bonds ->
            bonds?.let {
                viewModel.getMarkers(it)
            }
        },
        onMarkerClick = { markerId ->
            viewModel.showMovieDetails(
                id = markerId,
                content = {
                    MovieDetailsBottomSheet()
                }
            )
        }
    )
}

@Composable
private fun Map(
    viewModel: MapScreenViewModel,
    modifier: Modifier = Modifier,
    onBoundsChange: (LatLngBounds?) -> Unit,
    onMarkerClick: (id: String) -> Unit
) {
    val markers: List<Movie> by remember(viewModel.screenState.markers) {
        mutableStateOf(viewModel.screenState.markers)
    }

    val cameraPositionState = rememberCameraPositionState() {
        position = CameraPosition.fromLatLngZoom(PARIS_POSITION, 11f)
    }
    val uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember {
        mutableStateOf(
            MapProperties(
                mapType = MapType.NORMAL,
                mapStyleOptions = MapStyleOptions(mapStyle)
            )
        )
    }

    var heatmapTileProvider: HeatmapTileProvider? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = viewModel.screenState) {
        viewModel.screenState.let { screenState ->
            screenState.heatmapTileProvider?.let {
                heatmapTileProvider = it
            }
        }
    }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            onBoundsChange(
                cameraPositionState.projection?.visibleRegion?.latLngBounds
            )
        }
    }

    Box(modifier) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            uiSettings = uiSettings,
            properties = properties,
            cameraPositionState = cameraPositionState
        ) {
            heatmapTileProvider?.let {
                TileOverlay(tileProvider = it)
            }

            markers.forEach { movie ->
                Marker(
                    state = MarkerState(position = LatLng(movie.lat, movie.lon)),
                    snippet = movie.title,
                    tag = movie.id,
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE),
                    onClick = {
                        onMarkerClick(it.tag as String)
                        true
                    }
                )
            }
        }
    }
}
