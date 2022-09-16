package com.eziosoft.parisinnumbers.presentation.ui.mapScreen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.eziosoft.parisinnumbers.navigation.Destination

fun NavGraphBuilder.mapScreen() {
    composable(
        route = Destination.MAP_SCREEN.name
    ) {
        MapScreen()
    }
}
