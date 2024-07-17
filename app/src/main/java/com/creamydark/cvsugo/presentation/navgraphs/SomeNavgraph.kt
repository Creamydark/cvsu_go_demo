package com.creamydark.cvsugo.presentation.navgraphs

import androidx.compose.material3.Text
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.creamydark.cvsugo.presentation.screens.coursesoffered.CourseDetailScreen
import com.creamydark.cvsugo.presentation.screens.coursesoffered.CoursesOfferedRootScreen
import com.creamydark.cvsugo.presentation.screens.coursesoffered.viewmodel.CourseDetailViewModel
import com.creamydark.cvsugo.presentation.screens.home.HomeRootScreen


fun NavGraphBuilder.home(navHostController: NavHostController){
        navigation(route = MainGraph.Home.route, startDestination = HomeScreens.RootScreen.route){
            composable(route = HomeScreens.RootScreen.route){
                HomeRootScreen(navHostController = navHostController)
            }
        }
}

fun NavGraphBuilder.courses(navHostController: NavHostController){
    navigation(route = MainGraph.Courses.route, startDestination = CoursesScreens.RootScreen.route){
        composable(route = CoursesScreens.RootScreen.route){
            CoursesOfferedRootScreen()
        }
        composable(
            route = CoursesScreens.CourseDetailScreen.route.plus("/{id}"),
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                },
            ),
        ){
            val id = it.arguments?.getString("id")
            val viewModel : CourseDetailViewModel = hiltViewModel()
            CourseDetailScreen(navHostController = navHostController,viewModel = viewModel)
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