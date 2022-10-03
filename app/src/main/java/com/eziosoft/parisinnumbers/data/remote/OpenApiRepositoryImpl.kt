package com.eziosoft.parisinnumbers.data.remote

import com.eziosoft.parisinnumbers.data.remote.openApi.MoviesAPI
import com.eziosoft.parisinnumbers.data.remote.openApi.OpenApiDataset
import com.eziosoft.parisinnumbers.data.toMovie
import com.eziosoft.parisinnumbers.domain.Movie
import com.eziosoft.parisinnumbers.domain.repository.OpenApiRepository

class OpenApiRepositoryImpl(private val api: MoviesAPI) : OpenApiRepository {
    override suspend fun getAllMovies(): Result<List<Movie>?> {
        val response = api.getAllMovies(datasets = OpenApiDataset.MOVIES.title)
        return if (response.isSuccessful) {
            Result.success(response.body()?.map { it.toMovie() })
        } else {
            Result.failure(Exception(response.message()))
        }
    }
}
