package com.creamydark.cvsugo.core.presentation.rootscreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.creamydark.cvsugo.accountSetup.presentation.setupnewaccount.AccountSetupRootScreen
import com.creamydark.cvsugo.community.components.CommunitySectionTopBar
import com.creamydark.cvsugo.core.domain.enums.AuthenticationState.Loading
import com.creamydark.cvsugo.core.domain.enums.AuthenticationState.OnRegister
import com.creamydark.cvsugo.core.presentation.loading.LoadingScreen
import com.creamydark.cvsugo.core.presentation.rootscreen.components.RootBottomNavigationComponent
import com.creamydark.cvsugo.core.presentation.rootscreen.components.TopBarCustomComponent
import com.creamydark.cvsugo.core.presentation.rootscreen.components.TopBarCustomComponent0
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.CommunityRoutesItems
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.NotificationRoutesItems
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.PortalRoutesItems
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.RoutesV2
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.UniversityRoutesItems
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.auth
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.community
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.googleAuth
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.notification
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.profile
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.studentNavGraph
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.universityNavGraph
import com.creamydark.cvsugo.core.presentation.rootscreen.viewmodel.MainScreenViewModel
import com.creamydark.cvsugo.portal.components.PortalTopBarComponent
import com.creamydark.cvsugo.university.components.UniversityPageTopbar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RootNavhostComponent(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = hiltViewModel()
) {

    val firebaseUser by viewModel.firebaseUser.collectAsStateWithLifecycle()

    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()

    val authState by viewModel.authenticationState.collectAsStateWithLifecycle()

    val navhostController = LocalNavController.current

    val navBackStackEntry by navhostController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination


    Log.d("currentDestination", "RootNavhostComponent: ${currentDestination?.route}")
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {

            when (currentDestination?.route) {
                CommunityRoutesItems.PostDetail.route.plus("/{postId}") -> {}
                RoutesV2.CreatePostScreen.route -> {}
                RoutesV2.CoursesOfferDetailScreen.route.plus("/{id}") -> {}
                RoutesV2.AccountSetupScreen.route->{}
                RoutesV2.LoadingScreen.route->{}
                else -> {
                    RootBottomNavigationComponent(user = firebaseUser)
                }
            }

        },
        topBar = {

            when(currentDestination?.route){
                in CommunityRoutesItems.entries.map { it.route } -> {
                    CommunitySectionTopBar(firebaseUser = firebaseUser)
                }
                in PortalRoutesItems.entries.map { it.route } -> {
                    PortalTopBarComponent(authenticationState = authState )
                }
                in NotificationRoutesItems.entries.map { it.route } -> {
                    TopBarCustomComponent0()
                }
                in UniversityRoutesItems.entries.map { it.route  } -> {
                    UniversityPageTopbar()
                }
                else->{
                    TopBarCustomComponent()
                }
            }

        }
    ){
        innerPadding ->
        val startd = when (authState) {
            Loading -> {
                RoutesV2.LoadingScreen.route
            }
            OnRegister -> {
                RoutesV2.AccountSetupScreen.route
            }
            else -> {
                RoutesV2.UniversityGraph.route
            }
        }
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            navController = navhostController,
            startDestination = startd
        ) {
            universityNavGraph(navhostController)
            notification(navhostController)
            composable(route = RoutesV2.AccountSetupScreen.route){
                AccountSetupRootScreen()
            }
            composable(route = RoutesV2.LoadingScreen.route){
                LoadingScreen()
            }
            studentNavGraph()
            community()
            auth()
            googleAuth()
            profile()
        }
    }
}
