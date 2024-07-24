package com.creamydark.cvsugo.core.presentation.rootscreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.creamydark.cvsugo.core.domain.enums.AuthenticationState
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.HomeScreensNavigationItems
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.MainGraph
import com.creamydark.cvsugo.core.presentation.rootscreen.viewmodel.MainScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawerComposable(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: MainScreenViewModel,
    drawerState: DrawerState
) {

    val scope = rememberCoroutineScope()

    val authState by viewModel.authenticationState.collectAsStateWithLifecycle()

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val hPadding32 = Modifier.padding(horizontal = 28.dp)
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
        HomeScreensNavigationItems.entries.forEach { screen ->
            NavigationDrawerItem(
                modifier = hPadding12,
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navHostController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navHostController.graph.findStartDestination().id)
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                    }.also {
                        scope.launch {
                            drawerState.close()
                        }
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
        if (authState == AuthenticationState.Authenticated){
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
                        popUpTo(navHostController.graph.findStartDestination().id)
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                    }.also {
                        scope.launch {
                            drawerState.close()
                        }
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
                Icon(imageVector = MainGraph.Development.icon, contentDescription = "")
            },
            selected = currentDestination?.hierarchy?.any { it.route == MainGraph.Development.route } == true,
            onClick = {
                navHostController.navigate(MainGraph.Development.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navHostController.graph.findStartDestination().id)
                    launchSingleTop = true
                }.also {
                    scope.launch {
                        drawerState.close()
                    }
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

        if (authState == AuthenticationState.Unauthenticated){
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
                    navHostController.navigate(MainGraph.Account.route){
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navHostController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }.also {
                        scope.launch {
                            drawerState.close()
                        }
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
                    viewModel.setLoginState(false).also {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                },
            )
        }
    }
}