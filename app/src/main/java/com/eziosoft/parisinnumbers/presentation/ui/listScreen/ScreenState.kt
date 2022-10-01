package com.eziosoft.parisinnumbers.presentation.ui.listScreen

data class ScreenState(
    val isLoading: Boolean = false,
    val items: List<MovieTitle> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0
)
