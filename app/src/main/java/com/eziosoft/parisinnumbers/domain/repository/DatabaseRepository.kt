package com.eziosoft.parisinnumbers.domain.repository

import com.eziosoft.parisinnumbers.domain.Movie

interface DatabaseRepository {
    suspend fun fillDb()
    suspend fun getAll(): List<Movie>
    suspend fun getPaged(rowNumber: Int, pageSize: Int, searchString: String): List<Movie>
}
