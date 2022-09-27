package com.eziosoft.parisinnumbers.presentation.ui.mainScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.eziosoft.parisinnumbers.presentation.ui.theme.Accent
import com.eziosoft.parisinnumbers.presentation.ui.theme.PrimaryLight

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector
)

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    itemsList: List<BottomNavItem>,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    BottomNavigation(
        modifier = modifier,
        elevation = 5.dp

    ) {
        itemsList.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        Icon(imageVector = item.icon, contentDescription = null)
                        AnimatedVisibility(visible = selected) {
                            Text(item.name)
                        }
                    }
                },
                selected = selected,
                selectedContentColor = PrimaryLight,
                unselectedContentColor = Accent,
                onClick = { onItemClick(item) }
            )
        }
    }
}
