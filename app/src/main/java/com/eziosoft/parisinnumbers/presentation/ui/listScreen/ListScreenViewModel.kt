package com.eziosoft.parisinnumbers.presentation.ui.listScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eziosoft.parisinnumbers.data.remote.openApi.PAGE_SIZE
import com.eziosoft.parisinnumbers.domain.Movie
import com.eziosoft.parisinnumbers.domain.paginator.DefaultPaginator
import com.eziosoft.parisinnumbers.domain.repository.DBState
import com.eziosoft.parisinnumbers.domain.repository.LocalDatabaseRepository
import com.eziosoft.parisinnumbers.navigation.Action
import com.eziosoft.parisinnumbers.navigation.ActionDispatcher
import com.eziosoft.parisinnumbers.presentation.ProjectDispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListScreenViewModel(
    private val dbRepository: LocalDatabaseRepository,
    private val actionDispatcher: ActionDispatcher,
    private val projectDispatchers: ProjectDispatchers
) : ViewModel() {

    var state by mutableStateOf(ScreenState())
        private set

    private val searchFlow = MutableStateFlow("")
    private var searchString = ""

    init {
        viewModelScope.launch {
            dbRepository.dbStateFlow.collect() {
                state = state.copy(dbState = it)

                when (it) {
                    DBState.Unknown -> Unit
                    DBState.Updating -> Unit
                    DBState.Ready -> observeSearch()
                }
            }
        }
    }

    private val paginator = DefaultPaginator<Int, List<Movie>>(
        initialPageIndex = state.page,
        onLoadingStatusChangeListener = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            withContext(projectDispatchers.ioDispatcher) {
                Result.success(dbRepository.getPaged(nextPage, PAGE_SIZE, searchString))
            }
        },
        getNextPageIndex = {
            state.items.size + 1
        },
        onError = {
            state = state.copy(error = it?.message)
        },
        onSuccess = { newPage, newKey ->
            state = state.copy(
                items = state.items + newPage,
                page = newKey,
                endReached = newPage.isEmpty()
            )
        }
    )

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextPage()
        }
    }

    fun search(text: String) {
        viewModelScope.launch(projectDispatchers.mainDispatcher) {
            searchFlow.emit(text)
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearch() {
        viewModelScope.launch(projectDispatchers.mainDispatcher) {
            searchFlow.debounce(1500).collect {
                searchString = it.trim()
                paginator.reset()
                state = state.copy(items = emptyList())
                paginator.loadNextPage()
            }
        }
    }

//    fun searchInfoAboutMovie(title: String, callback: (posterUrl: String) -> Unit) =
//        viewModelScope.launch(projectDispatchers.ioDispatcher) {
//            movieDbRepository.search(title).onSuccess { list ->
//                list?.let { listOfMovies ->
//                    if (listOfMovies.isNotEmpty()) {
//                        listOfMovies.forEach { movie ->
//                            if (movie.title?.uppercase() == title.uppercase() && movie.poster_path != null) {
//                                callback(movie.poster_path)
//                            }
//                        }
//                    }
//                }
//            }.onFailure {
//                Log.d("aaa", "searchInfoAboutMovie: isFailure ${it.message}")
//            }
//        }

    fun showMovieDetails(id: String, content: @Composable () -> Unit) {
        viewModelScope.launch {
            actionDispatcher.sharedParameters.selectedMovieId.value = id
            actionDispatcher.sharedParameters.bottomSheetContent.value = content
            actionDispatcher.dispatchAction(Action.ToggleBottomSheet(true))
        }
    }
}
