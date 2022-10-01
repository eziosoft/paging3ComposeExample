package com.eziosoft.parisinnumbers.presentation.ui.listScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eziosoft.parisinnumbers.data.DefaultPaginator
import com.eziosoft.parisinnumbers.data.remote.openApi.PAGE_SIZE
import com.eziosoft.parisinnumbers.data.remote.openApi.models.records.MoviesPage
import com.eziosoft.parisinnumbers.data.remote.openApi.models.titles.MovieTitles
import com.eziosoft.parisinnumbers.data.toMovie
import com.eziosoft.parisinnumbers.domain.repository.OpenApiRepository
import com.eziosoft.parisinnumbers.domain.repository.TheMovieDbRepository
import com.eziosoft.parisinnumbers.navigation.Action
import com.eziosoft.parisinnumbers.navigation.ActionDispatcher
import com.eziosoft.parisinnumbers.navigation.Destination
import com.eziosoft.parisinnumbers.presentation.ProjectDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.net.URLDecoder

class ListScreenViewModel(
    private val repository: OpenApiRepository,
    private val movieDbRepository: TheMovieDbRepository,
    private val actionDispatcher: ActionDispatcher,
    private val projectDispatchers: ProjectDispatchers
) : ViewModel() {

    var state by mutableStateOf(ScreenState())
        private set

    private val searchFlow = MutableStateFlow("")
    private var searchString = ""

    private val paginator = DefaultPaginator<Int, MovieTitles>(
        initialPageIndex = state.page,
        onLoadingStatusChangeListener = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            withContext(projectDispatchers.ioDispatcher) {
                repository.getTitles(nextPage, searchString, PAGE_SIZE)
            }
        },
        getNextPageIndex = {
            state.items.size
//            getNextPageIndex(it)
        },
        onError = {
            state = state.copy(error = it?.message)
        },
        onSuccess = { newPage, newKey ->
            val listOfMovies = newPage.records.map { MovieTitle(it.record.fields.nom_tournage) }
            state = state.copy(
                items = state.items + listOfMovies,
                page = newKey,
                endReached = newPage.records.isEmpty()
            )
        }
    )

    init {
        observeSearch()
        loadNextItems()
    }

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
                if (it.isNotEmpty()) {
                    searchString = it
                    paginator.reset()
                    state = state.copy(items = emptyList())
                    paginator.loadNextPage()
                }
            }
        }
    }

    fun searchInfoAboutMovie(title: String, callback: (posterUrl: String) -> Unit) =
        viewModelScope.launch(projectDispatchers.ioDispatcher) {
            movieDbRepository.search(title).onSuccess { list ->
                list?.let { listOfMovies ->
                    if (listOfMovies.isNotEmpty()) {
                        listOfMovies.forEach { movie ->
                            if (movie.title?.uppercase() == title.uppercase() && movie.poster_path != null) {
                                callback(movie.poster_path)
                            }
                        }
                    }
                }
            }.onFailure {
                Log.d("aaa", "searchInfoAboutMovie: isFailure ${it.message}")
            }
        }

    fun navigateToDetails(recordId: String) {
        viewModelScope.launch(projectDispatchers.mainDispatcher) {
            actionDispatcher.sharedParameters.recordId = recordId
            actionDispatcher.dispatchAction(Action.Navigate(Destination.DETAILS_SCREEN))
        }
    }

    private fun splitQuery(url: URL): Map<String, String> {
        val queryPairs: MutableMap<String, String> = LinkedHashMap()
        val query = url.query
        val pairs = query.split("&").toTypedArray()
        for (pair in pairs) {
            val idx = pair.indexOf("=")
            queryPairs[URLDecoder.decode(pair.substring(0, idx), "UTF-8")] =
                URLDecoder.decode(pair.substring(idx + 1), "UTF-8")
        }
        return queryPairs
    }

    private fun getNextPageIndex(page: MovieTitles) =
        splitQuery(URL(page.links.find { it.rel == "next" }?.href))["offset"]
            ?.toInt() ?: 0
}
