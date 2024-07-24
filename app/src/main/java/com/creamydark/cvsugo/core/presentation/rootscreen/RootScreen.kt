package com.creamydark.cvsugo.core.presentation.rootscreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.creamydark.cvsugo.core.presentation.rootscreen.components.NavigationDrawerComposable
import com.creamydark.cvsugo.core.presentation.rootscreen.viewmodel.MainScreenViewModel

@Composable
fun RootScreen(modifier: Modifier = Modifier) {
    val viewModel: MainScreenViewModel = hiltViewModel()
    val navHostController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawerComposable(
                navHostController = navHostController,
                viewModel = viewModel,
                drawerState = drawerState
            )
        },
    ) {
        MainScreen(
            modifier = Modifier.fillMaxWidth(),
            navHostController = navHostController,
            drawaState = drawerState,
            viewModel = viewModel
        )
    }
}