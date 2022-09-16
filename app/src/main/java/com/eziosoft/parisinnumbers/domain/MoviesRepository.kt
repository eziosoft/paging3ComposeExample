package com.eziosoft.parisinnumbers.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun searchMovieByTitle(title: String)
    fun getMovies(): Flow<PagingData<Movie>>
    suspend fun getMovie(id: String): Result<Movie?>
    suspend fun getAllMovies(): Result<List<Movie>?>
}
