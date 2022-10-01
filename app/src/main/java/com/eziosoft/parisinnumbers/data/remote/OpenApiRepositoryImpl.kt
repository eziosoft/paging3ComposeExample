package com.eziosoft.parisinnumbers.data.remote

import com.eziosoft.parisinnumbers.data.remote.openApi.MoviesAPI
import com.eziosoft.parisinnumbers.data.remote.openApi.OpenApiDataset
import com.eziosoft.parisinnumbers.data.remote.openApi.PAGE_SIZE
import com.eziosoft.parisinnumbers.data.remote.openApi.models.records.MoviesPage
import com.eziosoft.parisinnumbers.data.remote.openApi.models.titles.MovieTitles
import com.eziosoft.parisinnumbers.data.toMovie
import com.eziosoft.parisinnumbers.data.toResult
import com.eziosoft.parisinnumbers.domain.Movie
import com.eziosoft.parisinnumbers.domain.repository.OpenApiRepository

class OpenApiRepositoryImpl(private val api: MoviesAPI) : OpenApiRepository {
    override suspend fun getMovie(id: String): Result<Movie?> {
        val response = api.getMovieById(datasets = OpenApiDataset.MOVIES.title, recordId = id)
        return if (response.isSuccessful) {
            Result.success(response.body()?.toMovie())
        } else {
            Result.failure(Exception(response.message()))
        }
    }

    override suspend fun getAllMovies(): Result<List<Movie>?> {
        val response = api.getAllMovies(datasets = OpenApiDataset.MOVIES.title)
        return if (response.isSuccessful) {
            Result.success(response.body()?.map { it.toMovie() })
        } else {
            Result.failure(Exception(response.message()))
        }
    }

    override suspend fun getPage(
        pageNumber: Int,
        searchQuery: String,
        pageSize: Int
    ): Result<MoviesPage> {
        val response = api.getPage(
            dataset = OpenApiDataset.MOVIES.title,
            pageStartItem = pageNumber,
            pageSize = PAGE_SIZE,
            where = if (searchQuery.isEmpty()) null else "nom_tournage like \"$searchQuery\"",
            orderBy = "date_debut desc",
            groupBy = null
        )

        return response.toResult()
    }

    override suspend fun getTitles(
        pageNumber: Int,
        searchQuery: String,
        pageSize: Int
    ): Result<MovieTitles> {
        val response = api.getMovieTitlesPage(
            dataset = OpenApiDataset.MOVIES.title,
            pageStartItem = pageNumber,
            pageSize = PAGE_SIZE,
            where = if (searchQuery.isEmpty()) null else "nom_tournage like \"$searchQuery\"",
            orderBy = null,
            groupBy = "nom_tournage"
        )

        return response.toResult()
    }
}
