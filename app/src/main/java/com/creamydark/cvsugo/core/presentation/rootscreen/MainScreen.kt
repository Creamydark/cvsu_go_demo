package com.creamydark.cvsugo.core.presentation.rootscreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.creamydark.cvsugo.accountSetup.presentation.setupnewaccount.AccountSetupRootScreen
import com.creamydark.cvsugo.community.components.CommunitySectionTopBar
import com.creamydark.cvsugo.community.profile.component.ProfileScreenTopBar
import com.creamydark.cvsugo.core.domain.enums.AuthenticationState.Loading
import com.creamydark.cvsugo.core.domain.enums.AuthenticationState.OnRegister
import com.creamydark.cvsugo.core.presentation.loading.LoadingScreen
import com.creamydark.cvsugo.core.presentation.rootscreen.components.RootBottomNavigationComponent
import com.creamydark.cvsugo.core.presentation.rootscreen.components.TopBarCustomComponent
import com.creamydark.cvsugo.core.presentation.rootscreen.components.TopBarCustomComponent0
import com.creamydark.cvsugo.core.presentation.rootscreen.intent.MainScreenIntent
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.CommunityRoutesItems
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.NotificationRoutesItems
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.PortalRoutesItems
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.ProfileRoutesItems
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.RootRoutesItems
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.RoutesV2
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.SignInRoutesItems
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.UniversityRoutesItems
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.auth
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.community
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.googleAuth
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.notification
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.profile
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.student
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.universityNavGraph
import com.creamydark.cvsugo.core.presentation.rootscreen.viewmodel.MainScreenViewModel
import com.creamydark.cvsugo.core.presentation.state.MainScreenState
import com.creamydark.cvsugo.googleAuth.domain.dataclass.UserData
import com.creamydark.cvsugo.notification.components.NotificationScreenTopBar
import com.creamydark.cvsugo.portal.components.PortalTopBarComponent
import com.creamydark.cvsugo.university.components.UniversityPageTopbar
import kotlinx.coroutines.flow.collectLatest


@Composable
fun MainScreen(modifier: Modifier = Modifier,viewModel: MainScreenViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val navhostController = LocalNavController.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.event.collectLatest { event ->
                when (event) {
                    is MainScreenEvent.ShowError -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    }
                    MainScreenEvent.NavigateToLogin -> {
                        // Handle navigation
                    }

                    MainScreenEvent.NavigateToUniversity -> {
                        navigateToUniversity(navhostController)
                    }
                }
            }
        }
    }

    RootNavhostComponent(
        modifier = modifier,
        state = state,
        onIntent = viewModel::handleIntent,
        navhostController = navhostController
    )

}

private fun navigateToUniversity(navhostController: NavHostController){
    navhostController.navigate(RootRoutesItems.University.route) {
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


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun RootNavhostComponent(
    modifier: Modifier = Modifier,
    state: MainScreenState = MainScreenState(),
    onIntent: (MainScreenIntent) -> Unit = {},
    navhostController: NavHostController
) {
    val firebaseUser = state.firebaseUser
    val currentUser = state.currentUser
    val portalAuthState = state.portalAuthState

    val navBackStackEntry by navhostController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination


    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {

            when (currentDestination?.route) {
                in ProfileRoutesItems.entries.map { it.route } -> {}
                SignInRoutesItems.SignIn.route -> {}
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
                ProfileRoutesItems.Profile.route ->{
                    ProfileScreenTopBar(
                        title = currentUser?.username ?: "",
                        onSignOut = {
                            onIntent(MainScreenIntent.SignOut)
                        }
                    )
                }
                RoutesV2.LoadingScreen.route -> {}
                CommunityRoutesItems.CreatePost.route -> {
                    TopBarCustomComponent(title = "Create Post")
                }
                in NotificationRoutesItems.entries.filterNot { it.route == NotificationRoutesItems.UploadNotification.route }.map { it.route } -> {
                    NotificationScreenTopBar()
                }
                in CommunityRoutesItems.entries.filterNot { it.route == CommunityRoutesItems.CreatePost.route }.map { it.route } -> {
                    CommunitySectionTopBar(userData = currentUser?: UserData())
                }
                in PortalRoutesItems.entries.map { it.route } -> {
                    PortalTopBarComponent(authenticationState = portalAuthState)
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
        val startDestination = when (state.authenticationState) {
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
            startDestination = startDestination
        ) {
            universityNavGraph()
            notification()
            composable(route = RoutesV2.AccountSetupScreen.route){
                AccountSetupRootScreen()
            }
            composable(route = RoutesV2.LoadingScreen.route){
                LoadingScreen()
            }
            student()
            community()
            auth()
            googleAuth()
            profile()
        }
    }
}
