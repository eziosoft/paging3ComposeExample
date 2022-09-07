package com.eziosoft.parisinnumbers.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig

class MoviesRepository(private val api: MoviesAPI) {
    fun getMovies() = Pager(
        pagingSourceFactory = { MoviesPagingSource(api, Datasets.MOVIES) },
        config = PagingConfig(pageSize = PAGE_SIZE)
    ).flow
}
