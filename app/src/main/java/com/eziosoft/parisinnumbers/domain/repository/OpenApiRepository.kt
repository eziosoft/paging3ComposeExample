package com.eziosoft.parisinnumbers.domain.repository

import com.eziosoft.parisinnumbers.domain.Movie

interface OpenApiRepository {
    suspend fun getAllMovies(): Result<List<Movie>?>
}
