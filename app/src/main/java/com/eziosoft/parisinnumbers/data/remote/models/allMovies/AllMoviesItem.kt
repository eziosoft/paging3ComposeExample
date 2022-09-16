package com.eziosoft.parisinnumbers.data.remote.models.allMovies

data class AllMoviesItem(
    val adresse_lieu: String?,
    val annee_tournage: String?,
    val ardt_lieu: String?,
    val coord_x: Double,
    val coord_y: Double,
    val date_debut: String?,
    val date_fin: String?,
    val geo_point_2d: GeoPoint2d,
    val geo_shape: GeoShape,
    val id_lieu: String?,
    val nom_producteur: String?,
    val nom_realisateur: String?,
    val nom_tournage: String?,
    val type_tournage: String?
)
