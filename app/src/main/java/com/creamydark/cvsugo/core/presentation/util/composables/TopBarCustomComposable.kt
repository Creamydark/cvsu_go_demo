package com.creamydark.cvsugo.core.presentation.util.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.creamydark.cvsugo.R
import com.creamydark.cvsugo.core.presentation.mainscreen.navgraphs.AnnouncementScreens
import com.creamydark.cvsugo.core.presentation.mainscreen.navgraphs.HomeScreens
import com.creamydark.cvsugo.core.presentation.mainscreen.navgraphs.StudentPortalScreens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarCustomComposable(modifier: Modifier = Modifier, navHostController: NavHostController, drawaState: DrawerState,) {

    val scope = rememberCoroutineScope()


    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val minju = listOf(
        HomeScreens.MainScreen.route,
        HomeScreens.CoursesOfferScreen.route,
        HomeScreens.AboutUniversityScreen.route,
        StudentPortalScreens.StudentHome.route
    )
    TopAppBar(
        modifier = modifier,
        title = {

        },
        navigationIcon = {
            if (currentDestination?.route in minju){
                IconButton(onClick = {  }) {
                    Image(modifier = Modifier.size(32.dp), painter = painterResource(id = R.drawable.cvsu_clean_logo), contentDescription = "")
                }
            }
            if (currentDestination?.route !in minju){
                IconButton(
                    onClick = {
                        navHostController.popBackStack()
                    },
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "")
                }
            }
        },
        actions = {
            IconButton(
                onClick = {
                    navHostController.navigate(AnnouncementScreens.ListScreens.route){
                        launchSingleTop = true
                    }
                },
            ) {
                Icon(imageVector = Icons.Outlined.Notifications, contentDescription = "")
            }
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
}