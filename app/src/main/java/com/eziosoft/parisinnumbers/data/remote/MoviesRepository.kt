package com.eziosoft.parisinnumbers.data.remote

import androidx.paging.PagingData
import com.eziosoft.parisinnumbers.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMovies(): Flow<PagingData<Movie>>
    suspend fun getMovie(id: String): Result<Movie?>
    suspend fun getAllMovies(): Result<List<Movie>?>
}
