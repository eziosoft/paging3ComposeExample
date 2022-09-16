package com.eziosoft.parisinnumbers.presentation.ui.mapScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

private val PARIS_POSITION = LatLng(48.8566, 2.3522)

@Composable
fun MapScreen() {
    val cameraPositionState = rememberCameraPositionState() {
        position = CameraPosition.fromLatLngZoom(PARIS_POSITION, 11f)
    }
    val uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }

    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            uiSettings = uiSettings,
            properties = properties,
            cameraPositionState = cameraPositionState
        )
    }
}
