package com.creamydark.cvsugo.presentation.screens.mainscreen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.creamydark.cvsugo.presentation.navgraphs.MainGraph
import com.creamydark.cvsugo.presentation.navgraphs.about
import com.creamydark.cvsugo.presentation.navgraphs.account
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

    val isUserLoggedIn by viewModel.isUserLoggedIn().collectAsStateWithLifecycle(initialValue = false)

    LaunchedEffect(key1 = isUserLoggedIn) {
        Log.d("MainScreen", "MainScreen: $isUserLoggedIn")
    }

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    //Main Screen
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarCustomComposable(navHostController = navHostController, drawaState = drawaState)
        },
        bottomBar = {

        }
    ) { innerPadding ->
        val startDestination = if (isUserLoggedIn) MainGraph.StudentPortal.route else MainGraph.Home.route
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navHostController,
            startDestination = startDestination
        ){
            home(navHostController = navHostController)
            about(navHostController = navHostController)
            courses(navHostController = navHostController)
            appInfo(navHostController = navHostController)
            account(navHostController = navHostController)
            studentPortal(navHostController = navHostController)
        }
    }
}