package com.eziosoft.parisinnumbers.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.eziosoft.parisinnumbers.data.remote.models.Record
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl(private val api: MoviesAPI) : MoviesRepository {
    override fun getMovies(): Flow<PagingData<Record>> = Pager(
        pagingSourceFactory = { MoviesPagingSource(api, Datasets.MOVIES) },
        config = PagingConfig(pageSize = PAGE_SIZE)
    ).flow
}
