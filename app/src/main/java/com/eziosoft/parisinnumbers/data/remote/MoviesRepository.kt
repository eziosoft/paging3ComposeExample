package com.eziosoft.parisinnumbers.data.remote

import androidx.paging.PagingData
import com.eziosoft.parisinnumbers.data.remote.models.allMovies.AllMovies
import com.eziosoft.parisinnumbers.data.remote.models.records.Record
import com.eziosoft.parisinnumbers.data.remote.models.singleRecord.SingleRecord
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMovies(): Flow<PagingData<Record>>
    suspend fun getMovie(id: String): Result<SingleRecord?>
    suspend fun getAllMovies(id: String): Result<AllMovies?>
}
