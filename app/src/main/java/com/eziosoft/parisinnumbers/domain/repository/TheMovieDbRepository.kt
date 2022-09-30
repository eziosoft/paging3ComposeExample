package com.eziosoft.parisinnumbers.domain.repository

import com.eziosoft.parisinnumbers.domain.TheMovieDbResult

interface TheMovieDbRepository {

    suspend fun search(query: String): Result<List<TheMovieDbResult>?>
}
