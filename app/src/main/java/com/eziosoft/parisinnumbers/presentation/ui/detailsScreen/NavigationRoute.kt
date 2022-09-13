package com.eziosoft.parisinnumbers.presentation.ui.detailsScreen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.eziosoft.parisinnumbers.navigation.Destination
import com.eziosoft.parisinnumbers.presentation.ui.mainScreen.composableWithArguments

fun NavGraphBuilder.detailsScreen() {
    composable(route = Destination.DETAILS_SCREEN.name){
        DetailsScreen()
    }
}
