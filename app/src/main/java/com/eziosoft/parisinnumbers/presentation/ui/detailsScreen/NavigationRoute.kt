package com.eziosoft.parisinnumbers.presentation.ui.detailsScreen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.eziosoft.parisinnumbers.navigation.Destination

fun NavGraphBuilder.detailsScreen() {
    composable(route = Destination.DETAILS_SCREEN.name) {
        DetailsScreen()
    }
}
