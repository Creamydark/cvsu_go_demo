package com.creamydark.cvsugo.core.presentation.rootscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

val LocalNavController = compositionLocalOf<NavHostController> { error("No NavController found!") }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        val navController = rememberNavController()
        CompositionLocalProvider(LocalNavController provides navController) {
            MainScreen()
        }
    }
}