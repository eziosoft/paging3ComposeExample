package com.eziosoft.parisinnumbers.data.remote

import com.eziosoft.parisinnumbers.data.remote.theMovieDb.TheMovieDb
import com.eziosoft.parisinnumbers.data.toTheMovieDBResult
import com.eziosoft.parisinnumbers.domain.TheMovieDbResult
import com.eziosoft.parisinnumbers.domain.repository.TheMovieDbRepository

class TheMovieDbRepositoryImpl(private val api: TheMovieDb, private val apiKey: String) :
    TheMovieDbRepository {

    override suspend fun search(query: String): Result<List<TheMovieDbResult>?> {
        val response = api.search(apiKey, query)

        return if (response.isSuccessful) {
            Result.success(
                response.body()?.results?.map {
                    it.toTheMovieDBResult()
                }
            )
        } else {
            Result.failure(Exception(response.message()))
        }
    }
}
