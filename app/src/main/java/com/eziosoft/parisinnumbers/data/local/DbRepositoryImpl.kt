package com.eziosoft.parisinnumbers.data.local

import com.eziosoft.parisinnumbers.data.local.room.movies.MovieDao
import com.eziosoft.parisinnumbers.data.toMovie
import com.eziosoft.parisinnumbers.data.toRoomMovie
import com.eziosoft.parisinnumbers.domain.Movie
import com.eziosoft.parisinnumbers.domain.repository.DBState
import com.eziosoft.parisinnumbers.domain.repository.DatabaseRepository
import com.eziosoft.parisinnumbers.domain.repository.OpenApiRepository
import com.eziosoft.parisinnumbers.domain.repository.TheMovieDbRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DbRepositoryImpl(
    private val movieDao: MovieDao,
    private val openApiRepository: OpenApiRepository,
    private val theMovieDbRepository: TheMovieDbRepository
) : DatabaseRepository {

    private val _dbStateFlow = MutableStateFlow(DBState.Unknown)
    override val dbStateFlow: StateFlow<DBState> = _dbStateFlow.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            if (movieDao.isRoomEmpty()) {
                _dbStateFlow.emit(DBState.Updating)
                fillDb()
                _dbStateFlow.emit(DBState.Ready)
            } else {
                _dbStateFlow.emit(DBState.Ready)
            }
        }
    }

    private suspend fun fillDb() {
        val allMoviesResult = openApiRepository.getAllMovies()
        allMoviesResult.onSuccess { movies ->
            movies?.let { listOfMovies ->
                movieDao.insertAll(listOfMovies.map { it.toRoomMovie() })
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
                    posterUrl = movieDetails.posterUrl,
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
        movieDao.getPaged(rowNumber, pageSize, searchString).parallelMap {
            val movieDetails = theMovieDbRepository.search(it.title)
            if (movieDetails != null) {
                it.toMovie().copy(
                    posterUrl = movieDetails.posterUrl,
                    description = movieDetails.description
                )
            } else {
                it.toMovie()
            }
        }

    private suspend fun <A, B> Iterable<A>.parallelMap(f: suspend (A) -> B): List<B> =
        coroutineScope {
            map { async { f(it) } }.awaitAll()
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
