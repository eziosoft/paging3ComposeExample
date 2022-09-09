package com.eziosoft.parisinnumbers.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class NavigateTo {
    LIST_SCREEN, DETAILS_SCREEN
}

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavigateTo.LIST_SCREEN.name
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        listScreen(navController)
        detailsScreen()
    }
}

fun NavGraphBuilder.listScreen(navController: NavHostController) {
    composable(
        route = NavigateTo.LIST_SCREEN.name
    ) {
        ListScreen(
            onItemClick = { id ->
                navController.navigate(NavigateTo.DETAILS_SCREEN.name + "/$id")
            }
        )
    }
}

fun NavGraphBuilder.detailsScreen() {
    composableWithArguments(
        NavigateTo.DETAILS_SCREEN.name,
        listOf(navArgument("recordId") { type = NavType.StringType })
    ) {
        DetailsScreen(it.arguments?.getString("recordId"))
    }
}

fun NavGraphBuilder.composableWithArguments(
    route: String,
    arguments: List<NamedNavArgument>,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    val listOfArguments = arguments.map { it.name }.joinToString("/", "{", "}")
    val routeWithArgs = "$route/$listOfArguments"

    composable(route = routeWithArgs, arguments = arguments, content = content)
}
