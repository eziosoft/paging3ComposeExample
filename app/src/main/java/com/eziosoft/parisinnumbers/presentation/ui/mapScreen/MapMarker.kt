package com.eziosoft.parisinnumbers.presentation.ui.mapScreen

import com.google.android.gms.maps.model.LatLng

data class MapMarker(
    val position: LatLng,
    val name: String
)
