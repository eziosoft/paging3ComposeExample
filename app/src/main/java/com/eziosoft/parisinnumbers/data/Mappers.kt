package com.eziosoft.parisinnumbers.data

import com.eziosoft.parisinnumbers.data.local.room.movies.RoomMovie
import com.eziosoft.parisinnumbers.domain.Movie

fun com.eziosoft.parisinnumbers.data.remote.openApi.models.allMovies.AllMoviesItem.toMovie() =
    Movie(
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

fun Movie.toRoomMovie() = RoomMovie(
    id = placeId,
    address = address,
    year = year,
    ardt_lieu = ardt_lieu,
    lon = lon,
    lat = lat,
    startDate = startDate,
    endDate = endDate,
    placeId = placeId,
    producer = producer,
    realisation = realisation,
    title = title,
    type = type

)

fun RoomMovie.toMovie() = Movie(
    id = placeId,
    address = address,
    year = year,
    ardt_lieu = ardt_lieu,
    lon = lon,
    lat = lat,
    startDate = startDate,
    endDate = endDate,
    placeId = placeId,
    producer = producer,
    realisation = realisation,
    title = title,
    type = type
)
