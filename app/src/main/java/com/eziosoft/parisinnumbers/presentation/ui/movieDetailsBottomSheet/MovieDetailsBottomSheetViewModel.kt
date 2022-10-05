package com.eziosoft.parisinnumbers.presentation.ui.movieDetailsBottomSheet

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eziosoft.parisinnumbers.domain.Movie
import com.eziosoft.parisinnumbers.domain.repository.DatabaseRepository
import com.eziosoft.parisinnumbers.navigation.ActionDispatcher
import com.eziosoft.parisinnumbers.presentation.ProjectDispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ScreenState(val movie: Movie = Movie())

class MovieDetailsBottomSheetViewModel(
    private val projectDispatchers: ProjectDispatchers,
    private val dbRepository: DatabaseRepository,
    val actionDispatcher: ActionDispatcher
) : ViewModel() {

    var state by mutableStateOf(ScreenState())
        private set

    init {
        getMovieById()
    }

    fun getMovieById() {
        Log.d("aaa", "getMovieById: ${actionDispatcher.sharedParameters.selectedMovieId}")
        viewModelScope.launch(projectDispatchers.ioDispatcher) {
            val movie = dbRepository.getMovie(actionDispatcher.sharedParameters.selectedMovieId)
            movie?.let {
                withContext(projectDispatchers.mainDispatcher) {
                    state = state.copy(movie = it)
                }
            }
        }
    }
}