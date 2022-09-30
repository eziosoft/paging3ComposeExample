package com.eziosoft.parisinnumbers.data.remote.openApi.models.records

data class GeoShape(
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)
