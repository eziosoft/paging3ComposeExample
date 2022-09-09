package com.eziosoft.parisinnumbers.data.remote.models.records

data class GeoShape(
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)
