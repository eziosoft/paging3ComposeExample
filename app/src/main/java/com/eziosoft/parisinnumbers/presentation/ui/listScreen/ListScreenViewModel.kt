package com.eziosoft.parisinnumbers.presentation.ui.listScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eziosoft.parisinnumbers.data.remote.MoviesRepository
import com.eziosoft.parisinnumbers.data.remote.models.records.Record
import com.eziosoft.parisinnumbers.navigation.Action
import com.eziosoft.parisinnumbers.navigation.ActionDispatcher
import com.eziosoft.parisinnumbers.navigation.Destination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ListScreenViewModel(
    private val repository: MoviesRepository,
    private val actionDispatcher: ActionDispatcher
) : ViewModel() {
    fun getMovies(): Flow<PagingData<Record>> = repository.getMovies().cachedIn(viewModelScope)

    fun navigateToDetails(recordId: String) {
        viewModelScope.launch {
            actionDispatcher.sharedParameters.recordId = recordId
            actionDispatcher.dispatchAction(Action.Navigate(Destination.DETAILS_SCREEN))
        }
    }
}
