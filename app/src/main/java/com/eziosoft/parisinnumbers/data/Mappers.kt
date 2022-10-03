package com.eziosoft.parisinnumbers.data

import com.eziosoft.parisinnumbers.data.local.room.RoomMovie
import com.eziosoft.parisinnumbers.data.remote.theMovieDb.models.MovieResult
import com.eziosoft.parisinnumbers.domain.Movie
import com.eziosoft.parisinnumbers.domain.TheMovieDbResult

fun MovieResult.toTheMovieDBResult() =
    TheMovieDbResult(
        adult = adult,
        backdrop_path = backdrop_path,
        genre_ids = genre_ids,
        id = id,
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = if (poster_path != null) "https://image.tmdb.org/t/p/w500$poster_path" else null,
        release_date = release_date,
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count
    )

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
