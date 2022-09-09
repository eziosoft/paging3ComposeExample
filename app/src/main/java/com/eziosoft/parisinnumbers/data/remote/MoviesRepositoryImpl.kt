package com.eziosoft.parisinnumbers.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.eziosoft.parisinnumbers.data.remote.models.allMovies.AllMovies
import com.eziosoft.parisinnumbers.data.remote.models.records.Record
import com.eziosoft.parisinnumbers.data.remote.models.singleRecord.SingleRecord
import com.eziosoft.parisinnumbers.data.toResult
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl(private val api: MoviesAPI) : MoviesRepository {
    override fun getMovies(): Flow<PagingData<Record>> = Pager(
        pagingSourceFactory = { MoviesPagingSource(api, Datasets.MOVIES) },
        config = PagingConfig(pageSize = PAGE_SIZE)
    ).flow

    override suspend fun getMovie(id: String): Result<SingleRecord?> =
        api.getMovieById(datasets = Datasets.MOVIES.title, recordId = id).toResult()

    override suspend fun getAllMovies(): Result<AllMovies?> =
        api.getAllMovies(datasets = Datasets.MOVIES.title).toResult()
}
