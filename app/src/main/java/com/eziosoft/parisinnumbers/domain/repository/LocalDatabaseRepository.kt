package com.eziosoft.parisinnumbers.domain.repository

import com.eziosoft.parisinnumbers.domain.Movie
import kotlinx.coroutines.flow.StateFlow

interface LocalDatabaseRepository {
    val dbStateFlow: StateFlow<DBState>
    suspend fun getAll(): List<Movie>
    suspend fun getMovie(id: String): Movie?
    suspend fun getPaged(rowNumber: Int, pageSize: Int, searchString: String): List<Movie>
    suspend fun getByLocation(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double,
        numberOfResults: Int
    ): List<Movie>
}

enum class DBState {
    Unknown, Updating, Ready
}
