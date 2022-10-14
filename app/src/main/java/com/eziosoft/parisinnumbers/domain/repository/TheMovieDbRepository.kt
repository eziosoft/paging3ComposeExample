package com.eziosoft.parisinnumbers.domain.repository

import com.eziosoft.parisinnumbers.data.local.room.movieDetails.RoomMovieDetails

interface TheMovieDbRepository {

    suspend fun search(query: String): RoomMovieDetails?
}
