package com.eziosoft.parisinnumbers.presentation.ui.mapScreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.compose.*
import com.google.maps.android.heatmaps.HeatmapTileProvider
import org.koin.androidx.compose.getViewModel

private val PARIS_POSITION = LatLng(48.8566, 2.3522)

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun MapScreen() {
    val context = LocalContext.current

    val viewModel: MapScreenViewModel = getViewModel()
    val cameraPositionState = rememberCameraPositionState() {
        position = CameraPosition.fromLatLngZoom(PARIS_POSITION, 11f)
    }
    val uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }

    var markers by remember {
        mutableStateOf(listOf<ClusterMarker>())
    }

    var heatmapTileProvider: HeatmapTileProvider? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = viewModel.screenState) {
        viewModel.screenState.let { screenState ->
            markers = screenState.markerList.map { ClusterMarker(it.position, it.name, it.name) }

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
            var clusterManager by remember { mutableStateOf<ClusterManager<ClusterMarker>?>(null) }
            MapEffect(key1 = markers) { googleMap ->
                clusterManager = ClusterManager<ClusterMarker>(context, googleMap)
                clusterManager?.let { clusterManager ->
                    clusterManager.setAnimation(false)
                    googleMap.setOnCameraIdleListener(clusterManager)
                    googleMap.setOnMarkerClickListener(clusterManager)
                    clusterManager.addItems(markers.map { ClusterMarker(it.position, "", "") })
                }
            }

            LaunchedEffect(key1 = cameraPositionState.isMoving) {
                if (!cameraPositionState.isMoving) {
                    clusterManager?.onCameraIdle()
                }
            }

            heatmapTileProvider?.let {
                TileOverlay(tileProvider = it)
            }
        }
    }
}
