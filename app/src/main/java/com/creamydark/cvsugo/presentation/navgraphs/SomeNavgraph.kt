package com.creamydark.cvsugo.presentation.navgraphs

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.creamydark.cvsugo.presentation.screens.about.AboutRootScreen
import com.creamydark.cvsugo.presentation.screens.announcements.AnnouncementListScreen
import com.creamydark.cvsugo.presentation.screens.appInfo.AppInfoRootScreen
import com.creamydark.cvsugo.presentation.screens.coursesoffered.CourseDetailScreen
import com.creamydark.cvsugo.presentation.screens.coursesoffered.CoursesOfferedRootScreen
import com.creamydark.cvsugo.presentation.screens.coursesoffered.viewmodel.CourseDetailViewModel
import com.creamydark.cvsugo.presentation.screens.home.HomeRootScreen
import com.creamydark.cvsugo.presentation.screens.signin.SignInScreen
import com.creamydark.cvsugo.presentation.screens.studentportal.homescreen.StudentPortalHomeScreen


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
            val viewModel : CourseDetailViewModel = hiltViewModel()
            CoursesOfferedRootScreen(navHostController = navHostController,viewModel = viewModel)
        }
        composable(
            route = CoursesScreens.CourseDetailScreen.route.plus("/{id}"),
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                },
            ),
        ){
            val id = it.arguments?.getString("id")?:""
            val viewModel : CourseDetailViewModel = hiltViewModel()
            viewModel.setCourseData(id)
            CourseDetailScreen(navHostController = navHostController,viewModel = viewModel)
        }
    }
}

fun NavGraphBuilder.about(navHostController: NavHostController){
    navigation(route = MainGraph.About.route, startDestination = AboutScreens.RootScreen.route){
        composable(route = AboutScreens.RootScreen.route){
            AboutRootScreen()
        }
    }
}


fun NavGraphBuilder.appInfo(navHostController: NavHostController){
    navigation(route = MainGraph.AppInfo.route, startDestination = AppInfoScreens.RootScreen.route){
        composable(route = AppInfoScreens.RootScreen.route){
            AppInfoRootScreen()
        }
    }
}

fun NavGraphBuilder.account(navHostController: NavHostController){
    navigation(route = MainGraph.Account.route, startDestination = AccountScreens.SignInScreen.route){
        composable(route = AccountScreens.SignInScreen.route){
            SignInScreen(navHostController)
        }
    }
}

fun NavGraphBuilder.studentPortal(navHostController: NavHostController){
    navigation(route = MainGraph.StudentPortal.route, startDestination = StudentPortalScreens.StudentHome.route){
        composable(route = StudentPortalScreens.StudentHome.route){
            StudentPortalHomeScreen(navHostController = navHostController)
        }
    }
}


fun NavGraphBuilder.announcements(navHostController: NavHostController){
    navigation(route = MainGraph.Announcement.route, startDestination = AnnouncementScreens.ListScreens.route){
        composable(route = AnnouncementScreens.ListScreens.route){
            AnnouncementListScreen(navHostController = navHostController)
        }
    }
}