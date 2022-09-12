package com.eziosoft.parisinnumbers.presentation.ui.listScreen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.eziosoft.parisinnumbers.navigation.Destination

fun NavGraphBuilder.listScreen() {
    composable(
        route = Destination.LIST_SCREEN.name
    ) {
        ListScreen()
    }
}
