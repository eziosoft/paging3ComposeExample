package com.eziosoft.parisinnumbers.data.remote.openApi.models.singleRecord

data class GeoShape(
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)
