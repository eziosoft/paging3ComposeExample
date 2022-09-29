package com.eziosoft.parisinnumbers.presentation.ui.detailsScreen

import android.os.Parcelable
import com.eziosoft.parisinnumbers.domain.Movie
import com.eziosoft.parisinnumbers.domain.TheMovieDbResult
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScreenState(
    val movieTitle: String = "",
    val address: String = "",
    val year: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val producer: String = "",
    val realisation: String = "",
    val type: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val infoAboutMovie: TheMovieDbResult? = null
) : Parcelable

fun Movie.toScreenState() = ScreenState(
    movieTitle = title,
    address = address,
    year = year,
    startDate = startDate,
    endDate = endDate,
    producer = producer,
    realisation = realisation,
    type = type,
    lat = lat,
    lon = lon
)
