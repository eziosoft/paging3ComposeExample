package com.eziosoft.parisinnumbers.data.local

import com.eziosoft.parisinnumbers.data.local.room.MovieDao
import com.eziosoft.parisinnumbers.data.toMovie
import com.eziosoft.parisinnumbers.data.toRoomMovie
import com.eziosoft.parisinnumbers.domain.Movie
import com.eziosoft.parisinnumbers.domain.repository.DatabaseRepository
import com.eziosoft.parisinnumbers.domain.repository.OpenApiRepository

class DbRepositoryImpl(
    private val movieDao: MovieDao,
    private val openApiRepository: OpenApiRepository
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

    override suspend fun getPaged(rowNumber: Int, pageSize: Int, searchString: String): List<Movie> =
        movieDao.getPaged(rowNumber, pageSize, searchString).map { it.toMovie() }
}
