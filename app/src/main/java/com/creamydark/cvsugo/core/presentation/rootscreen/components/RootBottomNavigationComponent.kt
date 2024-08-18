package com.creamydark.cvsugo.core.presentation.rootscreen.components

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.creamydark.cvsugo.core.presentation.rootscreen.LocalNavController
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.RootRoutesItems
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.RoutesV2
import com.google.firebase.auth.FirebaseUser

@Composable
fun RootBottomNavigationComponent(
    modifier: Modifier = Modifier,
    user: FirebaseUser? = null,
) {
    val navhostController = LocalNavController.current

    val navBackStackEntry by navhostController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination
    BottomAppBar(
        modifier = modifier,
    ) {
        RootRoutesItems.entries.forEach {
            rootRoutesItem: RootRoutesItems ->
            NavigationBarItem(
                icon = { Icon(imageVector = rootRoutesItem.icon, contentDescription = "") },
                label = { Text(text = rootRoutesItem.label) },
                selected = currentDestination?.hierarchy?.any { it.route == rootRoutesItem.route } == true,
                onClick = {
                    if (rootRoutesItem.route == RootRoutesItems.Community.route && user==null) {
                        navhostController.navigate(RoutesV2.GoogleAuthGraph.route) {
                            launchSingleTop = true
                        }
                    }else{
                        navhostController.navigate(rootRoutesItem.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navhostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}