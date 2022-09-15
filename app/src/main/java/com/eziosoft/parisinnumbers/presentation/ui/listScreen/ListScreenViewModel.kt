package com.eziosoft.parisinnumbers.presentation.ui.listScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eziosoft.parisinnumbers.data.remote.MoviesRepository
import com.eziosoft.parisinnumbers.domain.Movie
import com.eziosoft.parisinnumbers.navigation.Action
import com.eziosoft.parisinnumbers.navigation.ActionDispatcher
import com.eziosoft.parisinnumbers.navigation.Destination
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class ListScreenViewModel(
    private val repository: MoviesRepository,
    private val actionDispatcher: ActionDispatcher
) : ViewModel() {

    private val searchFlow = MutableStateFlow("")

    init {
        observeSearch()
    }

    fun getMovies(): Flow<PagingData<Movie>> = repository.getMovies().cachedIn(viewModelScope)

    fun search(text: String) {
        viewModelScope.launch { searchFlow.emit(text) }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearch() {
        viewModelScope.launch {
            searchFlow.debounce(1500).collect {
                repository.searchMovieByTitle(it)
            }
        }
    }

    fun navigateToDetails(recordId: String) {
        viewModelScope.launch {
            actionDispatcher.sharedParameters.recordId = recordId
            actionDispatcher.dispatchAction(Action.Navigate(Destination.DETAILS_SCREEN))
        }
    }
}
