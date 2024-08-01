package com.creamydark.cvsugo.core.presentation.rootscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.creamydark.cvsugo.core.presentation.rootscreen.components.RootBottomNavigationComponent
import com.creamydark.cvsugo.core.presentation.rootscreen.components.TopBarCustomComponent
import com.creamydark.cvsugo.core.presentation.rootscreen.components.TopBarCustomComponent0
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.RootRoutesItems
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.RoutesV2
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.auth
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.notification
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.profile
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.studentNavGraph
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.universityNavGraph
import com.creamydark.cvsugo.core.presentation.rootscreen.viewmodel.MainScreenViewModel
import com.creamydark.cvsugo.portal.components.PortalTopBarComponent
import com.creamydark.cvsugo.university.components.UniversityPageTopbar
import com.creamydark.cvsugo.university.presentation.coursesoffered.CourseDetailScreen
import com.creamydark.cvsugo.university.presentation.coursesoffered.viewmodel.CourseDetailViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RootNavhostComponent(modifier: Modifier = Modifier,viewModel: MainScreenViewModel = hiltViewModel()) {

    val authState by viewModel.authenticationState.collectAsStateWithLifecycle()

    val navhostController = LocalNavController.current

    val navBackStackEntry by navhostController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            if(currentDestination?.parent?.route in RootRoutesItems.entries.map { it.route }){
                RootBottomNavigationComponent(
                    navHostController = navhostController,
                    currenDestination = currentDestination
                )
            }
        },
        topBar = {
            when (currentDestination?.parent?.route) {
                RootRoutesItems.University.route -> {
                    UniversityPageTopbar( navHostController = navhostController,)
                }
                RootRoutesItems.Notification.route -> {
                    TopBarCustomComponent0()
                }
                RootRoutesItems.Portal.route -> {
                    PortalTopBarComponent(
                        modifier = Modifier.statusBarsPadding(),
                        navHostController = navhostController,
                        authenticationState = authState
                    )
                }
                RootRoutesItems.Profile.route -> {
                    TopBarCustomComponent0()
                }
                else -> { TopBarCustomComponent(navHostController = navhostController) }
            }
        },
    ){
            innerPadding ->

        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            navController = navhostController,
            startDestination = RootRoutesItems.University.route
        ) {
            universityNavGraph(navhostController)
            notification(navhostController)
            composable(route = RoutesV2.CoursesOfferDetailScreen.route.plus("/{id}"),
                listOf(navArgument("id") { type = NavType.StringType })
            ){
                    navBackStackEntry->
                val viewModel: CourseDetailViewModel = hiltViewModel(navBackStackEntry)
                CourseDetailScreen(navHostController = navhostController, viewModel = viewModel)
            }
            studentNavGraph(navhostController)
            profile(navhostController)
            auth()
        }

    }


}
