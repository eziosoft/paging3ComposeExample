package com.eziosoft.parisinnumbers.domain

interface TheMovieDbRepository {

    suspend fun search(query: String): Result<List<TheMovieDbResult>?>
}
