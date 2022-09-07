package com.eziosoft.parisinnumbers.data.remote

import androidx.paging.PagingData
import com.eziosoft.parisinnumbers.data.remote.models.Record
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMovies(): Flow<PagingData<Record>>
}
