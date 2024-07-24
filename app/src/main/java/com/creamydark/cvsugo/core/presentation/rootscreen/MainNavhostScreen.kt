package com.creamydark.cvsugo.core.presentation.rootscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.creamydark.cvsugo.core.domain.enums.AuthenticationState
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.MainGraph
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.account
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.announcements
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.development
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.home
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.studentPortal
import com.creamydark.cvsugo.core.presentation.rootscreen.viewmodel.MainScreenViewModel
import com.creamydark.cvsugo.core.presentation.util.composables.StudentBottomBar
import com.creamydark.cvsugo.core.presentation.util.composables.TopBarCustomComposable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    drawaState: DrawerState,
    viewModel: MainScreenViewModel
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val authState by viewModel.authenticationState.collectAsStateWithLifecycle()
    val startd = when (authState) {
        AuthenticationState.Authenticated -> MainGraph.StudentPortal.route
        AuthenticationState.Unauthenticated -> MainGraph.Home.route
        else -> MainGraph.SplashZero.route
    }
    //Main Screen
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarCustomComposable(navHostController = navHostController, drawaState = drawaState)
        },
        bottomBar = {
            if (navBackStackEntry?.destination?.parent?.route == MainGraph.StudentPortal.route){
                StudentBottomBar(
                    modifier = Modifier.fillMaxWidth(),
                    navHostController = navHostController
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navHostController,
            startDestination = startd
        ){
            account(navHostController = navHostController)
            home(navHostController = navHostController)
            studentPortal(navHostController = navHostController)
            announcements(navHostController = navHostController)
            development(navHostController = navHostController)
            composable(route = MainGraph.SplashZero.route){
                SplashScreen(modifier = modifier.fillMaxSize())
            }
        }
    }
}