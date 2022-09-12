package com.eziosoft.parisinnumbers.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eziosoft.parisinnumbers.presentation.navigation.Action
import com.eziosoft.parisinnumbers.presentation.navigation.ActionDispatcher
import com.eziosoft.parisinnumbers.presentation.navigation.Destination
import com.eziosoft.parisinnumbers.presentation.ui.detailsScreen.DetailsScreen
import com.eziosoft.parisinnumbers.presentation.ui.listScreen.ListScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationController(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destination.LIST_SCREEN.name,
    actionDispatcher: ActionDispatcher
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        actionDispatcher.actionFlow.collect() { action ->
            processAction(
                action,
                navController,
                actionDispatcher,
                coroutineScope,
                bottomSheetScaffoldState
            )
        }
    }

    BottomSheetScaffold(
        modifier = Modifier.padding(horizontal = 4.dp),
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            Box(
                modifier = Modifier.background(Color.LightGray)
            ) {
                Text("Sheet Content", fontSize = 80.sp)
            }
        },
        sheetPeekHeight = 0.dp
    ) { scaffoldPaddings ->
        NavHost(
            modifier = Modifier.padding(scaffoldPaddings),
            navController = navController,
            startDestination = startDestination
        ) {
            listScreen()
            detailsScreen()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
private fun processAction(
    action: Action,
    navController: NavHostController,
    actionDispatcher: ActionDispatcher,
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {
    when (action) {
        is Action.Navigate ->
            when (action.destination) {
                Destination.LIST_SCREEN -> navController.popBackStack(
                    Destination.LIST_SCREEN.name,
                    inclusive = false
                )
                Destination.DETAILS_SCREEN -> navController.navigate(
                    Destination.DETAILS_SCREEN.name + "/${actionDispatcher.recordId}"
                )
            }
        is Action.ToggleBottomSheet -> {
            coroutineScope.launch {
                if (action.expanded) {
                    bottomSheetScaffoldState.bottomSheetState.expand()
                } else {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }
        }
    }
}

private fun NavGraphBuilder.listScreen() {
    composable(
        route = Destination.LIST_SCREEN.name
    ) {
        ListScreen()
    }
}

private fun NavGraphBuilder.detailsScreen() {
    composableWithArguments(
        Destination.DETAILS_SCREEN.name,
        listOf(navArgument("recordId") { type = NavType.StringType })
    ) {
        DetailsScreen(it.arguments?.getString("recordId"))
    }
}
