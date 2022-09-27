package com.eziosoft.parisinnumbers.presentation.ui.mapScreen

import com.eziosoft.parisinnumbers.MainCoroutineRule
import com.eziosoft.parisinnumbers.domain.Movie
import com.eziosoft.parisinnumbers.domain.OpenApiRepository
import com.eziosoft.parisinnumbers.presentation.ProjectDispatchers
import com.google.android.gms.maps.model.LatLng
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
internal class MapScreenViewModelTest {

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

    private val movieList = listOf(sampleMovie)

    private val openApiRepository: OpenApiRepository = mockk {
        coEvery { getAllMovies() } returns Result.success(movieList)
    }
//    private val actionDispatcher: ActionDispatcher = ActionDispatcher(SharedParameters())
    private val projectDispatchers = ProjectDispatchers(
        mainDispatcher = Dispatchers.Main,
        ioDispatcher = Dispatchers.Main
    )

    @Test
    fun `check if viewmodel loads data`() = runTest {
        mockColorClass()

        val viewModel = MapScreenViewModel(
            repository = openApiRepository,
            projectDispatchers = projectDispatchers
        )

        advanceUntilIdle()
        val actualState = viewModel.screenState.markerList
        val expectedState = movieList.map {
            Marker(LatLng(it.lat, it.lon), name = it.title)
        }
        assertEquals(expectedState, actualState)
    }

    private fun mockColorClass() {
        mockkStatic(android.graphics.Color::class)
        every {
            android.graphics.Color.rgb(
                any() as Int,
                any(),
                any()
            )
        } returns android.graphics.Color.GRAY
        every { android.graphics.Color.red(any()) } returns 0
        every { android.graphics.Color.green(any()) } returns 0
        every { android.graphics.Color.blue(any()) } returns 0
        every { android.graphics.Color.alpha(any()) } returns 0
        every { android.graphics.Color.RGBToHSV(any(), any(), any(), any()) } returns Unit
        every { android.graphics.Color.HSVToColor(any(), any()) } returns 0
        every {
            android.graphics.Color.argb(
                any() as Int,
                any(),
                any(),
                any()
            )
        } returns android.graphics.Color.GRAY
    }
}
