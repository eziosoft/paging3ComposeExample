package com.eziosoft.parisinnumbers.domain.repository

import com.eziosoft.parisinnumbers.data.remote.openApi.models.records.MoviesPage
import com.eziosoft.parisinnumbers.data.remote.openApi.models.titles.MovieTitles
import com.eziosoft.parisinnumbers.domain.Movie

interface OpenApiRepository {
    suspend fun getMovie(id: String): Result<Movie?>
    suspend fun getAllMovies(): Result<List<Movie>?>
    suspend fun getPage(pageNumber: Int, searchQuery: String, pageSize: Int): Result<MoviesPage>
    suspend fun getTitles(pageNumber: Int, searchQuery: String, pageSize: Int): Result<MovieTitles>
}
