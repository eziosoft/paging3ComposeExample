package com.eziosoft.parisinnumbers.domain

import com.eziosoft.parisinnumbers.data.remote.OpenAPI.models.allMovies.AllMoviesItem
import com.eziosoft.parisinnumbers.data.remote.OpenAPI.models.records.RecordX
import com.eziosoft.parisinnumbers.data.remote.OpenAPI.models.singleRecord.SingleRecord

data class Movie(
    val id: String,
    val address: String,
    val year: String,
    val ardt_lieu: String,
    val lon: Double,
    val lat: Double,
    val startDate: String,
    val endDate: String,
    val placeId: String,
    val producer: String,
    val realisation: String,
    val title: String,
    val type: String
)

fun com.eziosoft.parisinnumbers.data.remote.OpenAPI.models.records.RecordX.toMovie() = Movie(
    id = id,
    address = fields.adresse_lieu ?: "",
    year = fields.annee_tournage ?: "",
    ardt_lieu = fields.ardt_lieu ?: "",
    lon = fields.geo_point_2d.lon,
    lat = fields.geo_point_2d.lat,
    startDate = fields.date_debut ?: "",
    endDate = fields.date_fin ?: "",
    placeId = fields.id_lieu ?: "",
    producer = fields.nom_producteur ?: "",
    realisation = fields.nom_realisateur ?: "",
    title = fields.nom_tournage ?: "",
    type = fields.type_tournage ?: ""
)

fun com.eziosoft.parisinnumbers.data.remote.OpenAPI.models.singleRecord.SingleRecord.toMovie() = Movie(
    id = record.id,
    address = record.fields.adresse_lieu,
    year = record.fields.annee_tournage,
    ardt_lieu = record.fields.ardt_lieu,
    lon = record.fields.geo_point_2d.lon,
    lat = record.fields.geo_point_2d.lat,
    startDate = record.fields.date_debut,
    endDate = record.fields.date_fin,
    placeId = record.fields.id_lieu,
    producer = record.fields.nom_producteur,
    realisation = record.fields.nom_realisateur,
    title = record.fields.nom_tournage,
    type = record.fields.type_tournage
)

fun com.eziosoft.parisinnumbers.data.remote.OpenAPI.models.allMovies.AllMoviesItem.toMovie() = Movie(
    id = "",
    address = adresse_lieu ?: "",
    year = annee_tournage ?: "",
    ardt_lieu = ardt_lieu ?: "",
    lon = geo_point_2d.lon,
    lat = geo_point_2d.lat,
    startDate = date_debut ?: "",
    endDate = date_fin ?: "",
    placeId = id_lieu ?: "",
    producer = nom_producteur ?: "",
    realisation = nom_realisateur ?: "",
    title = nom_tournage ?: "",
    type = type_tournage ?: ""
)
