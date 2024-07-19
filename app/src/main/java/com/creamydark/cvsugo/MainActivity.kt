package com.creamydark.cvsugo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.creamydark.cvsugo.presentation.screens.mainscreen.MainScreen
import com.creamydark.cvsugo.presentation.screens.mainscreen.viewmodel.MainScreenViewModel
import com.creamydark.cvsugo.presentation.screens.util.NavigationDrawerComposable
import com.creamydark.cvsugo.ui.theme.CVSUGoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CVSUGoTheme {
                val viewModel: MainScreenViewModel = hiltViewModel()
                val navHostController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        NavigationDrawerComposable(
                            navHostController = navHostController,
                            viewModel = viewModel
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
        }
    }
}