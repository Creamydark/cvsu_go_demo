package com.creamydark.cvsugo.presentation.screens.util.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.creamydark.cvsugo.presentation.navgraphs.AccountScreens
import com.creamydark.cvsugo.presentation.navgraphs.MainGraph
import com.creamydark.cvsugo.presentation.screens.mainscreen.viewmodel.MainScreenViewModel

@Composable
fun NavigationDrawerComposable(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: MainScreenViewModel
) {
    val isUserLoggedIn by viewModel.isUserLoggedIn().collectAsStateWithLifecycle(initialValue = false)

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val hPadding32 = Modifier.padding(horizontal = 28.dp)
    val nav_items = listOf(
        MainGraph.Home,
        MainGraph.Courses,
        MainGraph.About
    )
    val hPadding12 = Modifier.padding(horizontal = 12.dp)
    ModalDrawerSheet(
        modifier = modifier
    ){
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = hPadding32,
            text = "CVSU Go",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(8.dp))
        nav_items.forEach { screen ->
            NavigationDrawerItem(
                modifier = hPadding12,
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navHostController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }

                },
                icon = {
                    Icon(imageVector = screen.icon, contentDescription = "")
                },
                label = {
                    Text(
                        text = screen.label,
                    )
                }
            )
        }
        if (isUserLoggedIn){
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                modifier = Modifier.padding(horizontal = 28.dp),
                text = "Portal",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            NavigationDrawerItem(
                modifier = hPadding12,
                label = {
                    Text(
                        text = "Student",
                    )
                },
                icon = {
                    Icon(imageVector = MainGraph.StudentPortal.icon, contentDescription = "")
                },
                selected = currentDestination?.hierarchy?.any { it.route == MainGraph.StudentPortal.route } == true,
                onClick = {
                    navHostController.navigate(MainGraph.StudentPortal.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
            )
        }


        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            modifier = Modifier.padding(horizontal = 28.dp),
            text = "Development",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        NavigationDrawerItem(
            modifier = hPadding12,
            label = {
                Text(
                    text = "Developer",
                )
            },
            icon = {
                Icon(imageVector = MainGraph.AppInfo.icon, contentDescription = "")
            },
            selected = currentDestination?.hierarchy?.any { it.route == MainGraph.AppInfo.route } == true,
            onClick = {
                navHostController.navigate(MainGraph.AppInfo.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navHostController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            },
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Text(
            modifier = hPadding32,
            text = "Account",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        if (!isUserLoggedIn){
            NavigationDrawerItem(
                modifier = hPadding12,
                label = {
                    Text(
                        text = "Sign In",
                    )
                },
                icon = {
                    Icon(imageVector = Icons.Outlined.Lock, contentDescription = "")
                },
                selected = currentDestination?.hierarchy?.any { it.route == MainGraph.Account.route } == true,
                onClick = {
                    navHostController.navigate(AccountScreens.SignInScreen.route){
                        launchSingleTop = true
                    }
                },
            )
        }else{
            NavigationDrawerItem(
                modifier = hPadding12,
                label = {
                    Text(
                        text = "Sign out",
                    )
                },
                icon = {
                    Icon(imageVector = Icons.Outlined.Lock, contentDescription = "")
                },
                selected = false,
                onClick = {
                    viewModel.setLoginState(false)
                },
            )
        }
    }
}