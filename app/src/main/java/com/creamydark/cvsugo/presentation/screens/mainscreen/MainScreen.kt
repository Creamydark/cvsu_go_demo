package com.creamydark.cvsugo.presentation.screens.mainscreen

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
import com.creamydark.cvsugo.domain.enums.AuthenticationState
import com.creamydark.cvsugo.presentation.navgraphs.MainGraph
import com.creamydark.cvsugo.presentation.navgraphs.SplashScreens
import com.creamydark.cvsugo.presentation.navgraphs.about
import com.creamydark.cvsugo.presentation.navgraphs.account
import com.creamydark.cvsugo.presentation.navgraphs.announcements
import com.creamydark.cvsugo.presentation.navgraphs.appInfo
import com.creamydark.cvsugo.presentation.navgraphs.courses
import com.creamydark.cvsugo.presentation.navgraphs.home
import com.creamydark.cvsugo.presentation.navgraphs.studentPortal
import com.creamydark.cvsugo.presentation.screens.mainscreen.viewmodel.MainScreenViewModel
import com.creamydark.cvsugo.presentation.screens.util.composables.TopBarCustomComposable

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
        AuthenticationState.Loading -> SplashScreens.SplashZero.route
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
            about(navHostController = navHostController)
            courses(navHostController = navHostController)
            appInfo(navHostController = navHostController)
            account(navHostController = navHostController)
            studentPortal(navHostController = navHostController)
            announcements(navHostController = navHostController)
            composable(route = SplashScreens.SplashZero.route){
                SplashScreen(modifier = modifier.fillMaxSize())
            }
        }
    }
}