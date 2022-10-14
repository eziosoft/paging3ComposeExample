package com.eziosoft.parisinnumbers.data.local

import com.eziosoft.parisinnumbers.data.local.room.movies.MovieDao
import com.eziosoft.parisinnumbers.data.toMovie
import com.eziosoft.parisinnumbers.data.toRoomMovie
import com.eziosoft.parisinnumbers.domain.Movie
import com.eziosoft.parisinnumbers.domain.repository.DatabaseRepository
import com.eziosoft.parisinnumbers.domain.repository.OpenApiRepository
import com.eziosoft.parisinnumbers.domain.repository.TheMovieDbRepository

class DbRepositoryImpl(
    private val movieDao: MovieDao,
    private val openApiRepository: OpenApiRepository,
    private val theMovieDbRepository: TheMovieDbRepository
) : DatabaseRepository {

    override suspend fun fillDb() {
        val allMoviesResult = openApiRepository.getAllMovies()
        allMoviesResult.onSuccess { movies ->
            movies?.let {
                movieDao.insertAll(it.map { it.toRoomMovie() })
            }
        }
    }

    override suspend fun getAll(): List<Movie> =
        movieDao.getAll().map { it.toMovie() }

    override suspend fun getMovie(id: String): Movie? =
        movieDao.getMovie(id).map {
            val movieDetails = theMovieDbRepository.search(it.title)
            if (movieDetails != null) {
                it.toMovie().copy(
                    posterUrl = "https://image.tmdb.org/t/p/w500${movieDetails.posterUrl}",
                    description = movieDetails.description
                )
            } else {
                it.toMovie()
            }
        }.firstOrNull()

    override suspend fun getPaged(
        rowNumber: Int,
        pageSize: Int,
        searchString: String
    ): List<Movie> =
        movieDao.getPaged(rowNumber, pageSize, searchString).map {
            val movieDetails = theMovieDbRepository.search(it.title)
            if (movieDetails != null) {
                it.toMovie().copy(
                    posterUrl = "https://image.tmdb.org/t/p/w500${movieDetails.posterUrl}",
                    description = movieDetails.description
                )
            } else {
                it.toMovie()
            }
        }

    override suspend fun getByLocation(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double,
        numberOfResults: Int
    ): List<Movie> =
        movieDao.getByPosition(lat1, lon1, lat2, lon2, numberOfResults).map { it.toMovie() }
}
