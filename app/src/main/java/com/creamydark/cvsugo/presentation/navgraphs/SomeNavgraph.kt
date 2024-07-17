package com.creamydark.cvsugo.presentation.navgraphs

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.creamydark.cvsugo.presentation.screens.coursesoffered.CoursesOfferedRootScreen
import com.creamydark.cvsugo.presentation.screens.home.HomeRootScreen


fun NavGraphBuilder.home(navHostController: NavHostController){
        navigation(route = MainGraph.Home.route, startDestination = HomeScreens.RootScreen.route){
            composable(route = HomeScreens.RootScreen.route){
                HomeRootScreen()
            }
        }
}

fun NavGraphBuilder.courses(navHostController: NavHostController){
    navigation(route = MainGraph.Courses.route, startDestination = CoursesScreens.RootScreen.route){
        composable(route = CoursesScreens.RootScreen.route){
            CoursesOfferedRootScreen()
        }
    }
}

fun NavGraphBuilder.about(navHostController: NavHostController){
    navigation(route = MainGraph.About.route, startDestination = AboutScreens.RootScreen.route){
        composable(route = AboutScreens.RootScreen.route){
            Text(text = "About")
        }
    }
}