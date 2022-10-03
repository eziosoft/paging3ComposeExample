package com.eziosoft.parisinnumbers.presentation.ui.listScreen

import com.eziosoft.parisinnumbers.domain.Movie

data class ScreenState(
    val isLoading: Boolean = false,
    val items: List<Movie> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0
)
