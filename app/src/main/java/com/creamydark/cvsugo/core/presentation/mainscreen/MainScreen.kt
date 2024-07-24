package com.creamydark.cvsugo.core.presentation.mainscreen

import androidx.compose.foundation.layout.fillMaxSize
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
import com.creamydark.cvsugo.core.domain.enums.AuthenticationState
import com.creamydark.cvsugo.core.presentation.mainscreen.navgraphs.MainGraph
import com.creamydark.cvsugo.core.presentation.mainscreen.navgraphs.account
import com.creamydark.cvsugo.core.presentation.mainscreen.navgraphs.announcements
import com.creamydark.cvsugo.core.presentation.mainscreen.navgraphs.development
import com.creamydark.cvsugo.core.presentation.mainscreen.navgraphs.home
import com.creamydark.cvsugo.core.presentation.mainscreen.navgraphs.studentPortal
import com.creamydark.cvsugo.core.presentation.mainscreen.viewmodel.MainScreenViewModel
import com.creamydark.cvsugo.core.presentation.util.composables.TopBarCustomComposable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    drawaState: DrawerState,
    viewModel: MainScreenViewModel
) {

    val authState by viewModel.authenticationState.collectAsStateWithLifecycle()

    val startd = when (authState) {
        AuthenticationState.Authenticated -> MainGraph.StudentPortal.route
        AuthenticationState.Unauthenticated -> MainGraph.Home.route
        AuthenticationState.Loading -> MainGraph.SplashZero.route
        else -> MainGraph.Home.route
    }

    //Main Screen
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if(authState != AuthenticationState.Loading){
                TopBarCustomComposable(navHostController = navHostController, drawaState = drawaState)
            }
        },
        bottomBar = {

        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navHostController,
            startDestination = startd
        ){
            home(navHostController = navHostController)
            account(navHostController = navHostController)
            studentPortal(navHostController = navHostController)
            announcements(navHostController = navHostController)
            development(navHostController = navHostController)
            composable(route = MainGraph.SplashZero.route){
                SplashScreen(modifier = modifier.fillMaxSize())
            }
        }
    }
}