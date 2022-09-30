package com.eziosoft.parisinnumbers.data

import com.eziosoft.parisinnumbers.data.remote.theMovieDb.models.MovieResult
import com.eziosoft.parisinnumbers.domain.Movie
import com.eziosoft.parisinnumbers.domain.TheMovieDbResult
import retrofit2.Response

fun <T> Response<T>.toResult(): Result<T> {
    return if (isSuccessful) {
        Result.success(body()!!)
    } else {
        Result.failure(Exception(message()))
    }
}

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

fun com.eziosoft.parisinnumbers.data.remote.openApi.models.records.RecordX.toMovie() = Movie(
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

fun com.eziosoft.parisinnumbers.data.remote.openApi.models.singleRecord.SingleRecord.toMovie() =
    Movie(
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
