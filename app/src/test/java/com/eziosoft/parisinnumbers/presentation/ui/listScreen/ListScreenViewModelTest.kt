package com.eziosoft.parisinnumbers.presentation.ui.listScreen

import com.eziosoft.parisinnumbers.MainCoroutineRule
import com.eziosoft.parisinnumbers.domain.Movie
import com.eziosoft.parisinnumbers.domain.OpenApiRepository
import com.eziosoft.parisinnumbers.domain.TheMovieDbRepository
import com.eziosoft.parisinnumbers.navigation.ActionDispatcher
import com.eziosoft.parisinnumbers.navigation.SharedParameters
import com.eziosoft.parisinnumbers.presentation.ProjectDispatchers
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class ListScreenViewModelTest {

    @get:Rule
    val rule = MainCoroutineRule()

    private val sampleMovie = Movie(
        "id",
        "address",
        "year",
        "ardt",
        0.0,
        0.0,
        "startDate",
        "endDate",
        "placeId",
        "producer",
        "realisation",
        "title",
        "type"
    )

    private val openApiRepo: OpenApiRepository = mockk {
        coEvery { getMovie(any()) } returns Result.success(sampleMovie)
    }

    private val movieDbRepo: TheMovieDbRepository = mockk {
        coEvery { search(any(), any()) } returns Result.success(emptyList())
    }
    private val actionDispatcher: ActionDispatcher = ActionDispatcher(SharedParameters())
    private val projectDispatchers = ProjectDispatchers(
        mainDispatcher = Dispatchers.Main,
        ioDispatcher = Dispatchers.Main
    )

    @Test
    fun `search test`() = runTest {
        val viewModel = ListScreenViewModel(
            openApiRepo,
            movieDbRepo,
            actionDispatcher,
            projectDispatchers
        )

        advanceUntilIdle()
    }
}
