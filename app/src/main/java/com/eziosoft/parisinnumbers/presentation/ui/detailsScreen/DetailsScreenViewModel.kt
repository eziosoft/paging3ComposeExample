package com.eziosoft.parisinnumbers.presentation.ui.detailsScreen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eziosoft.parisinnumbers.data.remote.MoviesRepository
import com.eziosoft.parisinnumbers.domain.Movie
import com.eziosoft.parisinnumbers.navigation.Action
import com.eziosoft.parisinnumbers.navigation.ActionDispatcher
import com.eziosoft.parisinnumbers.navigation.Destination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    val lon: Double = 0.0
)

fun Movie.toScreenState() =
    ScreenState(
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
    private val repository: MoviesRepository,
    val actionDispatcher: ActionDispatcher
) : ViewModel() {
    private val _contentFlow = MutableStateFlow(ScreenState())
    val screenStateFlow = _contentFlow.asStateFlow()

    init {
        getMovie(actionDispatcher.sharedParameters.recordId)
    }

    private fun getMovie(id: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getMovie(id).onSuccess { record ->
            record?.let {
                _contentFlow.emit(it.toScreenState())
            }
        }
            .onFailure {
                Log.d("aaa", "getMovie: isFailure ${it.message}")
            }
    }

    fun navigateToList() {
        viewModelScope.launch {
            actionDispatcher.dispatchAction(Action.Navigate(Destination.LIST_SCREEN))
        }
    }

    fun showBottomSheet(content: @Composable () -> Unit) {
        viewModelScope.launch {
            actionDispatcher.sharedParameters.bottomSheetContent.value = content
            actionDispatcher.dispatchAction(Action.ToggleBottomSheet(true))
        }
    }

    fun hideBottomSheet() {
        viewModelScope.launch {
            actionDispatcher.dispatchAction(Action.ToggleBottomSheet(false))
        }
    }

    fun showSnackbar(text: String) {
        viewModelScope.launch {
            actionDispatcher.dispatchAction(Action.ShowSnackbar(text))
        }
    }
}
