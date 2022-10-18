package com.eziosoft.parisinnumbers.domain

data class Movie(
    val id: String = "",
    val address: String = "",
    val year: String = "",
    val ardt_lieu: String = "",
    val lon: Double = 0.0,
    val lat: Double = 0.0,
    val startDate: String = "",
    val endDate: String = "",
    val placeId: String = "",
    val producer: String = "",
    val realisation: String = "",
    val title: String = "",
    val type: String = "",

    val description: String = "",
    val posterUrl: String = ""
) {
    fun getFullPosterUrl() = POSTER_URL_PREFIX + posterUrl
}

private const val POSTER_URL_PREFIX = "https://image.tmdb.org/t/p/w500"
