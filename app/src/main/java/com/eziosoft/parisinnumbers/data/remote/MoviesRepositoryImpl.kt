package com.eziosoft.parisinnumbers.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.eziosoft.parisinnumbers.domain.Movie
import com.eziosoft.parisinnumbers.domain.toMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRepositoryImpl(private val api: MoviesAPI) : MoviesRepository {
    override fun getMovies(): Flow<PagingData<Movie>> = Pager(
        pagingSourceFactory = { MoviesPagingSource(api, Datasets.MOVIES) },
        config = PagingConfig(pageSize = PAGE_SIZE)
    ).flow.map { pagingData ->
        pagingData.map { record ->
            record.record.toMovie()
        }
    }

    override suspend fun getMovie(id: String): Result<Movie?> {
        val response = api.getMovieById(datasets = Datasets.MOVIES.title, recordId = id)
        return if (response.isSuccessful) {
            Result.success(response.body()?.toMovie())
        } else {
            Result.failure(Exception(response.message()))
        }
    }

    override suspend fun getAllMovies(): Result<List<Movie>?> {
        val response = api.getAllMovies(datasets = Datasets.MOVIES.title)
        return if (response.isSuccessful) {
            Result.success(response.body()?.map { it.toMovie() })
        } else {
            Result.failure(Exception(response.message()))
        }
    }
}
