package com.eziosoft.parisinnumbers.domain.repository

import com.eziosoft.parisinnumbers.data.local.room.movieDetails.LocalMovieDetails

interface TheMovieDbApiRepository {

    suspend fun search(query: String): LocalMovieDetails?
}
