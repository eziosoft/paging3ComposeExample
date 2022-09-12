package com.eziosoft.parisinnumbers.presentation.ui.detailsScreen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.eziosoft.parisinnumbers.presentation.composableWithArguments
import com.eziosoft.parisinnumbers.presentation.navigation.Destination

fun NavGraphBuilder.detailsScreen() {
    composableWithArguments(
        Destination.DETAILS_SCREEN.name,
        listOf(navArgument("recordId") { type = NavType.StringType })
    ) {
        DetailsScreen(it.arguments?.getString("recordId"))
    }
}
