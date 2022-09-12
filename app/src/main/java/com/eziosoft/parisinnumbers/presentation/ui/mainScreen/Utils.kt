package com.eziosoft.parisinnumbers.presentation.ui.mainScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.composableWithArguments(
    route: String,
    arguments: List<NamedNavArgument>,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    val listOfArguments = arguments.joinToString("/", "{", "}") { it.name }
    val routeWithArgs = "$route/$listOfArguments"

    composable(route = routeWithArgs, arguments = arguments, content = content)
}
