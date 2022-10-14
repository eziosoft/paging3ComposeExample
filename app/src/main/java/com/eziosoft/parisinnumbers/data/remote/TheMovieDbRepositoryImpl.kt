package com.eziosoft.parisinnumbers.data.remote

import android.util.Log
import com.eziosoft.parisinnumbers.data.local.room.movieDetails.MovieDetailsDao
import com.eziosoft.parisinnumbers.data.local.room.movieDetails.RoomMovieDetails
import com.eziosoft.parisinnumbers.data.remote.theMovieDb.TheMovieDb
import com.eziosoft.parisinnumbers.domain.repository.TheMovieDbRepository

class TheMovieDbRepositoryImpl(
    private val api: TheMovieDb,
    private val apiKey: String,
    private val movieDetailsDao: MovieDetailsDao
) : TheMovieDbRepository {

    override suspend fun search(query: String): RoomMovieDetails {
        val queryProcessed = query.uppercase().trim()

        val listFromRoom = movieDetailsDao.get(queryProcessed)
        Log.d("aaa", "search $queryProcessed: $listFromRoom")
        if (listFromRoom.isNotEmpty()) {
            return listFromRoom.first()
        } else {
            Log.i("aaa", "THE MOVIE DB CALL")
            val response = api.search(apiKey, query)

            if (response.isSuccessful) {
                Log.d("aaa", "search: response.isSuccessful")
                val body = response.body()
                val resultList = body?.results
                if (!resultList.isNullOrEmpty()) {
                    resultList.forEach {
                        if (it.title?.uppercase() == query.uppercase()) {
                            val movieFromApi = RoomMovieDetails(
                                title = queryProcessed,
                                posterUrl = it.poster_path ?: "",
                                description = it.overview ?: ""
                            )
                            movieDetailsDao.insert(movieFromApi)
                            return movieFromApi
                        }
                    }
                }
            }
        }

        val m = RoomMovieDetails(
            title = queryProcessed,
            posterUrl = "",
            description = ""
        )
        movieDetailsDao.insert(m)
        return m
    }
}

//    fun searchInfoAboutMovie(title: String, callback: (posterUrl: String) -> Unit) =
//        viewModelScope.launch(projectDispatchers.ioDispatcher) {
//            movieDbRepository.search(title).onSuccess { list ->
//                list?.let { listOfMovies ->
//                    if (listOfMovies.isNotEmpty()) {
//                        listOfMovies.forEach { movie ->
//                            if (movie.title?.uppercase() == title.uppercase() && movie.poster_path != null) {
//                                callback(movie.poster_path)
//                            }
//                        }
//                    }
//                }
//            }.onFailure {
//                Log.d("aaa", "searchInfoAboutMovie: isFailure ${it.message}")
//            }
//        }
