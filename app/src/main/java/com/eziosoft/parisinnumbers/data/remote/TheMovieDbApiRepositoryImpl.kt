package com.eziosoft.parisinnumbers.data.remote

import android.util.Log
import com.eziosoft.parisinnumbers.data.local.room.movieDetails.LocalMovieDetails
import com.eziosoft.parisinnumbers.data.local.room.movieDetails.MovieDetailsDao
import com.eziosoft.parisinnumbers.data.remote.theMovieDbApi.TheMovieDbApi
import com.eziosoft.parisinnumbers.domain.repository.TheMovieDbApiRepository

class TheMovieDbApiRepositoryImpl(
    private val api: TheMovieDbApi,
    private val apiKey: String,
    private val movieDetailsDao: MovieDetailsDao
) : TheMovieDbApiRepository {

    override suspend fun search(query: String): LocalMovieDetails {
        val queryProcessed = query.uppercase().trim()

        val listFromRoom = movieDetailsDao.get(queryProcessed)
        if (listFromRoom.isNotEmpty()) {
            return listFromRoom.first()
        } else {
            Log.i("aaa", "THE MOVIE DB CALL")
            val response = api.search(apiKey, query)

            if (response.isSuccessful) {
                val body = response.body()
                val resultList = body?.results
                if (!resultList.isNullOrEmpty()) {
                    resultList.forEach {
                        if (it.title?.uppercase() == query.uppercase()) {
                            val movieFromApi = LocalMovieDetails(
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

        val m = LocalMovieDetails(
            title = queryProcessed,
            posterUrl = "",
            description = ""
        )
        movieDetailsDao.insert(m)
        return m
    }
}
