package com.eziosoft.parisinnumbers.data.remote

import androidx.paging.*
import com.eziosoft.parisinnumbers.data.remote.OpenAPI.Datasets
import com.eziosoft.parisinnumbers.data.remote.OpenAPI.MoviesAPI
import com.eziosoft.parisinnumbers.data.remote.OpenAPI.PAGE_SIZE
import com.eziosoft.parisinnumbers.domain.Movie
import com.eziosoft.parisinnumbers.domain.MoviesAPIRepository
import com.eziosoft.parisinnumbers.domain.toMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesAPIRepositoryImpl(private val api: MoviesAPI) : MoviesAPIRepository {
    private var searchText = ""
    private var pagingSource: MoviesPagingSource = MoviesPagingSource(api, Datasets.MOVIES, searchText)

    override fun getMovies(): Flow<PagingData<Movie>> = Pager(
        pagingSourceFactory = {
            pagingSource = MoviesPagingSource(api, Datasets.MOVIES, searchText)
            pagingSource
        },
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

    override fun searchMovieByTitle(title: String) {
        searchText = title
        pagingSource.invalidate()
    }
}
