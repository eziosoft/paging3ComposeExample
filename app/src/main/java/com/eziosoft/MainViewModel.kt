package com.eziosoft

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eziosoft.parisinnumbers.data.remote.MoviesRepository
import com.eziosoft.parisinnumbers.data.remote.models.Record
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val repository: MoviesRepository) : ViewModel() {

    fun getMovies(): Flow<PagingData<Record>> = repository.getMovies().cachedIn(viewModelScope)
}
