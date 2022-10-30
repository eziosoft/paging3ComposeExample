package com.eziosoft.parisinnumbers.presentation.ui.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.eziosoft.parisinnumbers.navigation.Action
import com.eziosoft.parisinnumbers.navigation.ActionDispatcher
import com.eziosoft.parisinnumbers.navigation.Destination
import com.eziosoft.parisinnumbers.presentation.ui.listScreen.listScreen
import com.eziosoft.parisinnumbers.presentation.ui.mapScreen.mapScreen
import com.eziosoft.parisinnumbers.presentation.ui.theme.PrimaryLight
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationComponent(
    actionDispatcher: ActionDispatcher,
    modifier: Modifier = Modifier
) {
    val navController: NavHostController = rememberNavController()
    val startDestination: String = Destination.LIST_SCREEN.name

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        actionDispatcher.actionFlow.collect() { action ->
            processAction(
                action,
                navController,
                coroutineScope,
                bottomSheetScaffoldState
            )
        }
    }

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                    .background(PrimaryLight)
                    .padding(8.dp)
            ) {
                actionDispatcher.sharedParameters.bottomSheetContent.value()
            }
        },
        sheetPeekHeight = 0.dp
    ) { scaffoldPaddings1 ->
        var showMenu by remember { mutableStateOf(false) }

        Scaffold(
            modifier = Modifier
                .padding(scaffoldPaddings1),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            modifier = Modifier,
                            text = "Movies in Paris"
                        )
                    },
                    actions = {
                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "more")
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                modifier = Modifier
                                    .background(PrimaryLight),
                                onClick = {
                                    showMenu = false
                                }
                            ) {
                                Text("Refresh database")
                                Icon(Icons.Filled.Refresh, contentDescription = "Refresh database")
                            }
                        }
                    }
                )
            },
            bottomBar = {
                BottomNavigationBar(
                    modifier = Modifier,
                    navController = navController,
                    itemsList = listOf(
                        BottomNavItem(
                            name = "List",
                            route = Destination.LIST_SCREEN.name,
                            icon = Icons.Default.List
                        ),
                        BottomNavItem(
                            name = "Map",
                            route = Destination.MAP_SCREEN.name,
                            icon = Icons.Default.LocationOn
                        )
                    ),
                    onItemClick = { bottomNavItem ->
                        navController.navigate(bottomNavItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        ) { scaffoldPaddings2 ->
            NavHost(
                modifier = Modifier.padding(scaffoldPaddings2),
                navController = navController,
                startDestination = startDestination
            ) {
                listScreen()
                mapScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
private fun processAction(
    action: Action,
    navController: NavHostController,
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
                    Destination.DETAILS_SCREEN.name
                )
                Destination.MAP_SCREEN -> navController.popBackStack(
                    Destination.MAP_SCREEN.name,
                    inclusive = false
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
