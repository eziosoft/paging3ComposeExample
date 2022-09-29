package com.eziosoft.parisinnumbers.data.remote

import android.util.Log
import com.eziosoft.parisinnumbers.data.remote.theMovieDb.TheMovieDb
import com.eziosoft.parisinnumbers.domain.TheMovieDbRepository
import com.eziosoft.parisinnumbers.domain.TheMovieDbResult
import com.eziosoft.parisinnumbers.domain.toTheMovieDBResult

class TheMovieDbRepositoryImpl(private val api: TheMovieDb, private val apiKey: String) : TheMovieDbRepository {

    override suspend fun search(query: String): Result<List<TheMovieDbResult>?> {
        val response = api.search(apiKey, query)

        return if (response.isSuccessful) {
            Log.d("aaa", "search: ${response.body()} ")
            Result.success(
                response.body()?.results?.map {
                    Log.d("aaa", "search: $it")
                    it.toTheMovieDBResult()
                }
            )
        } else {
            Result.failure(Exception(response.message()))
        }
    }
}
