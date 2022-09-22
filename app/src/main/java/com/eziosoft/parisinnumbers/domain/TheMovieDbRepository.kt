package com.eziosoft.parisinnumbers.domain

interface TheMovieDbRepository {

    suspend fun search(
        query: String,
        apiKey: String
    ): Result<List<TheMovieDbResult>?>
}
