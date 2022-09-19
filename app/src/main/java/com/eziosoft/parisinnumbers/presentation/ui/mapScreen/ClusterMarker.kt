package com.eziosoft.parisinnumbers.presentation.ui.mapScreen

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class ClusterMarker(val itemPosition: LatLng, val itemTitle: String, val itemSnippet: String) :
    ClusterItem {
    override fun getPosition() = itemPosition
    override fun getTitle() = itemTitle
    override fun getSnippet() = itemSnippet
}
