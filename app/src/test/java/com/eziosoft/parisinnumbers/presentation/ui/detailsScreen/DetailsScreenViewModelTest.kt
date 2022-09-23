package com.eziosoft.parisinnumbers.presentation.ui.detailsScreen

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
import org.junit.rules.TestRule
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
internal class DetailsScreenViewModelTest {
    @get:Rule
    var rule: TestRule = MainCoroutineRule()

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

    private val actionDispatcher: ActionDispatcher = ActionDispatcher(SharedParameters())
    private val projectDispatchers = ProjectDispatchers(
        mainDispatcher = Dispatchers.Main,
        ioDispatcher = Dispatchers.Main
    )

    private lateinit var viewModel: DetailsScreenViewModel

    @Test
    fun `viewModel shows initial state`() = runTest {
        val openApiRepo: OpenApiRepository = mockk {
            coEvery { getMovie(any()) } returns Result.success(sampleMovie)
        }
        val movieDbRepo: TheMovieDbRepository = mockk {
            coEvery { search(any(), any()) } returns Result.success(emptyList())
        }

        viewModel = DetailsScreenViewModel(
            openApiRepository = openApiRepo,
            movieDbRepository = movieDbRepo,
            actionDispatcher = actionDispatcher,
            projectDispatchers = projectDispatchers
        )

        advanceUntilIdle()
        val exceptedState: ScreenState = sampleMovie.toScreenState()
        val actualState = viewModel.screenState
        assertEquals(exceptedState, actualState)
    }
}
