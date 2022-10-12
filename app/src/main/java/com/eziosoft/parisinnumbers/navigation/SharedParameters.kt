package com.eziosoft.parisinnumbers.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class SharedParameters(
    val selectedMovieId: MutableState<String> = mutableStateOf(""),
    val bottomSheetContent: MutableState<@Composable (() -> Unit)> = mutableStateOf({ })
)
