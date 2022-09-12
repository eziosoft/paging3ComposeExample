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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.eziosoft.parisinnumbers.presentation.navigation.Action
import com.eziosoft.parisinnumbers.presentation.navigation.ActionDispatcher
import com.eziosoft.parisinnumbers.presentation.navigation.Destination
import com.eziosoft.parisinnumbers.presentation.ui.detailsScreen.detailsScreen
import com.eziosoft.parisinnumbers.presentation.ui.listScreen.listScreen
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
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            BottomSheetContent()
//            actionDispatcher.sharedParameters.bottomSheetContent?.let { it() }
        },
        sheetPeekHeight = 0.dp
    ) { scaffoldPaddings ->
        NavHost(
            modifier = Modifier
                .padding(scaffoldPaddings)
                .padding(horizontal = 4.dp),
            navController = navController,
            startDestination = startDestination
        ) {
            listScreen()
            detailsScreen()
        }
    }
}

@Composable
private fun BottomSheetContent() {
    Box(
        modifier = Modifier.background(Color.LightGray)
    ) {
        Text("Sheet Content", fontSize = 80.sp)
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
                    Destination.DETAILS_SCREEN.name + "/${actionDispatcher.sharedParameters.recordId}"
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

        is Action.ShowSnackbar ->
            coroutineScope.launch {
                bottomSheetScaffoldState.snackbarHostState.showSnackbar(action.text)
            }
    }
}
