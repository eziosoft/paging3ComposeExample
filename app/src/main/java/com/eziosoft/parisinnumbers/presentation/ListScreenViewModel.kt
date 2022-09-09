package com.eziosoft.parisinnumbers.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eziosoft.parisinnumbers.data.remote.MoviesRepository
import com.eziosoft.parisinnumbers.data.remote.models.records.Record
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ListScreenViewModel(private val repository: MoviesRepository) : ViewModel() {
init {
    viewModelScope.launch(Dispatchers.IO){
        val a = repository.getAllMovies()
        a.onSuccess {

            Log.d("nnnn", "full database: ${it?.size}")
        }

    }
}

    fun getMovies(): Flow<PagingData<Record>> = repository.getMovies().cachedIn(viewModelScope)


}
