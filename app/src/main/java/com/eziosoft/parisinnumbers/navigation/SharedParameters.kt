package com.eziosoft.parisinnumbers.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.eziosoft.parisinnumbers.domain.Movie

data class SharedParameters(
    var recordId: String = "",
    val bottomSheetContent: MutableState<@Composable (() -> Unit)> = mutableStateOf({ }),
    var mapMovieList: List<Movie> = emptyList()
)
