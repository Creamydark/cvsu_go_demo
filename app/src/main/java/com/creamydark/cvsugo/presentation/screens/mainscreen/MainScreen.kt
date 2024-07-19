package com.creamydark.cvsugo.presentation.screens.mainscreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.creamydark.cvsugo.R
import com.creamydark.cvsugo.presentation.navgraphs.AboutScreens
import com.creamydark.cvsugo.presentation.navgraphs.AppInfoScreens
import com.creamydark.cvsugo.presentation.navgraphs.CoursesScreens
import com.creamydark.cvsugo.presentation.navgraphs.HomeScreens
import com.creamydark.cvsugo.presentation.navgraphs.MainGraph
import com.creamydark.cvsugo.presentation.navgraphs.about
import com.creamydark.cvsugo.presentation.navgraphs.account
import com.creamydark.cvsugo.presentation.navgraphs.appInfo
import com.creamydark.cvsugo.presentation.navgraphs.courses
import com.creamydark.cvsugo.presentation.navgraphs.home
import com.creamydark.cvsugo.presentation.screens.mainscreen.viewmodel.MainScreenViewModel
import kotlinx.coroutines.launch

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

    val scope = rememberCoroutineScope()
    val minju = listOf(
        HomeScreens.RootScreen.route,
        CoursesScreens.RootScreen.route,
        AboutScreens.RootScreen.route,
        AppInfoScreens.RootScreen.route,

        )
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    //Main Screen
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
//                        HomeTopBar(Modifier.padding(horizontal = 16.dp).statusBarsPadding().fillMaxWidth())
            TopAppBar(
                title = {
                    if (currentDestination?.route in minju){
                        IconButton(onClick = {  }) {
                            Image(painter = painterResource(id = R.drawable.cvsu_clean_logo), contentDescription = "")
                        }
                    }
                },
                navigationIcon = {
                    if (currentDestination?.route !in minju){
                        IconButton(
                            onClick = {
                                navHostController.navigateUp()
                            },
                        ) {
                            Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "")
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                drawaState.open()
                            }
                        },
                    ) {
                        Icon(imageVector = Icons.Outlined.Menu, contentDescription = "")
                    }
                }
            )
        },
        bottomBar = {

        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navHostController,
            startDestination = MainGraph.Home.route
        ){
            home(navHostController = navHostController)
            about(navHostController = navHostController)
            courses(navHostController)
            appInfo(navHostController = navHostController)
            account(navHostController)
        }
    }
}