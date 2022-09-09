package com.eziosoft.parisinnumbers.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailsScreen(movieId: String?) {
    val viewModel = getViewModel<DetailsScreenViewModel> { parametersOf(movieId) }
    val screenState = viewModel.screenStateFlow.collectAsState()

//    LaunchedEffect(key1 = true) {
//        movieId?.let { viewModel.getMovie(it) }
//    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column() {
            Text("Title: " + screenState.value.movieTitle)
            Text("Address: " + screenState.value.address)
            Text("Year: " + screenState.value.year)
            Text("Start date: " + screenState.value.startDate)
            Text("EndDate: " + screenState.value.endDate)
            Text("Producer: " + screenState.value.producer)
            Text("Realisation: " + screenState.value.realisation)
            Text("Type: " + screenState.value.type)
        }
    }
}
