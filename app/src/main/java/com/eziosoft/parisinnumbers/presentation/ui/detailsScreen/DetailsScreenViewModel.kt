package com.eziosoft.parisinnumbers.presentation.ui.detailsScreen

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eziosoft.parisinnumbers.domain.Movie
import com.eziosoft.parisinnumbers.domain.OpenApiRepository
import com.eziosoft.parisinnumbers.domain.TheMovieDbRepository
import com.eziosoft.parisinnumbers.domain.TheMovieDbResult
import com.eziosoft.parisinnumbers.navigation.Action
import com.eziosoft.parisinnumbers.navigation.ActionDispatcher
import com.eziosoft.parisinnumbers.navigation.Destination
import com.eziosoft.parisinnumbers.presentation.ProjectDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class ScreenState(
    val movieTitle: String = "",
    val address: String = "",
    val year: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val producer: String = "",
    val realisation: String = "",
    val type: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val infoAboutMovie: TheMovieDbResult? = null
)

fun Movie.toScreenState() = ScreenState(
    movieTitle = title,
    address = address,
    year = year,
    startDate = startDate,
    endDate = endDate,
    producer = producer,
    realisation = realisation,
    type = type,
    lat = lat,
    lon = lon
)

class DetailsScreenViewModel(
    private val openApiRepository: OpenApiRepository,
    private val movieDbRepository: TheMovieDbRepository,
    val actionDispatcher: ActionDispatcher,
    private val projectDispatchers: ProjectDispatchers
) : ViewModel() {

    init {
        getMovie(actionDispatcher.sharedParameters.recordId)
    }

    var screenState by mutableStateOf(ScreenState())
        private set


     fun getMovie(id: String) = viewModelScope.launch(projectDispatchers.ioDispatcher) {
        openApiRepository.getMovie(id).onSuccess { record ->
            record?.let {
                screenState = it.toScreenState()
                searchInfoAboutMovie(screenState.movieTitle)
            }
        }.onFailure {
            Log.d("aaa", "getMovie: isFailure ${it.message}")
        }
    }

    private fun searchInfoAboutMovie(title: String) = viewModelScope.launch(Dispatchers.IO) {
        movieDbRepository.search(title, "4582c6d7dbd578f026ba7614d760d566").onSuccess { list ->
            list?.let { listOfMovies ->
                if (listOfMovies.isNotEmpty()) {
                    listOfMovies.forEach { movie ->
                        if (movie.title?.uppercase() == title.uppercase()) {
                            screenState = screenState.copy(infoAboutMovie = movie)
                        }
                    }
                }
            }
        }.onFailure {
            Log.d("aaa", "searchInfoAboutMovie: isFailure ${it.message}")
        }
    }

    fun navigateToList() {
        viewModelScope.launch(projectDispatchers.mainDispatcher) {
            actionDispatcher.dispatchAction(Action.Navigate(Destination.LIST_SCREEN))
        }
    }

    fun showBottomSheet(content: @Composable () -> Unit) {
        viewModelScope.launch(projectDispatchers.mainDispatcher) {
            actionDispatcher.sharedParameters.bottomSheetContent.value = content
            actionDispatcher.dispatchAction(Action.ToggleBottomSheet(true))
        }
    }

    fun hideBottomSheet() {
        viewModelScope.launch(projectDispatchers.mainDispatcher) {
            actionDispatcher.dispatchAction(Action.ToggleBottomSheet(false))
        }
    }

    fun showSnackbar(text: String) {
        viewModelScope.launch(projectDispatchers.mainDispatcher) {
            actionDispatcher.dispatchAction(Action.ShowSnackbar(text))
        }
    }
}
