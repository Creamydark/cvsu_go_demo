package com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.creamydark.cvsugo.accountsignin.presentation.signin.SignInScreen
import com.creamydark.cvsugo.announcement.presentation.announcements.AnnouncementListScreen
import com.creamydark.cvsugo.development.presentation.developerInfo.DeveloperInfoScreen
import com.creamydark.cvsugo.home.presentation.about.AboutRootScreen
import com.creamydark.cvsugo.home.presentation.coursesoffered.CourseDetailScreen
import com.creamydark.cvsugo.home.presentation.coursesoffered.CoursesOfferedRootScreen
import com.creamydark.cvsugo.home.presentation.coursesoffered.viewmodel.CourseDetailViewModel
import com.creamydark.cvsugo.home.presentation.main.HomeMainScreen
import com.creamydark.cvsugo.portal.presentation.studentportal.defaultscreen.StudentPortalDashboardScreen
import com.creamydark.cvsugo.portal.presentation.studentportal.grades.StudentGradesScreen


fun NavGraphBuilder.home(navHostController: NavHostController){
    navigation(route = MainGraph.Home.route, startDestination = Routes.MainScreen.route){
        composable(route = Routes.MainScreen.route){
            Column {
                HomeMainScreen(navHostController = navHostController)
            }
        }
        composable(route = Routes.CoursesOfferScreen.route){
            CoursesOfferedRootScreen(navHostController = navHostController )
        }
        composable(
            route = Routes.CoursesOfferDetailScreen.route.plus("/{id}"),
            arguments = listOf(
                navArgument("id"){
                    type = NavType.StringType
                }
            ),
        ){
            val id = it.arguments?.getString("id")?:""
            val viewmodel : CourseDetailViewModel = hiltViewModel()
            viewmodel.setCourseData(id)
            CourseDetailScreen(
                navHostController = navHostController,
                viewModel = viewmodel
            )
        }
        composable(route = Routes.AboutUniversityScreen.route){
            AboutRootScreen()
        }
    }
}


fun NavGraphBuilder.account(navHostController: NavHostController){
    navigation(route = MainGraph.Account.route, startDestination = Routes.SignInScreen.route){
        composable(route = Routes.SignInScreen.route){
            SignInScreen(navHostController)
        }
    }
}

fun NavGraphBuilder.studentPortal(navHostController: NavHostController){
    navigation(route = MainGraph.StudentPortal.route, startDestination = Routes.StudentDashboardScreen.route){
        composable(route = Routes.StudentDashboardScreen.route){
            StudentPortalDashboardScreen(navHostController = navHostController)
        }
        composable(route = Routes.StudentGradesScreen.route){
            StudentGradesScreen()
        }
        composable(route = Routes.StudentProfileScreen.route){
            Text(text = "StudentProfile")
        }
    }
}

fun NavGraphBuilder.development(navHostController: NavHostController){
    navigation(route = MainGraph.Development.route, startDestination = Routes.DeveloperScreen.route){
        composable(route = Routes.DeveloperScreen.route){
            DeveloperInfoScreen()
        }
    }
}
fun NavGraphBuilder.announcements(navHostController: NavHostController){
    navigation(route = MainGraph.Announcement.route, startDestination = Routes.AnnouncementListScreen.route){
        composable(route = Routes.AnnouncementListScreen.route){
            AnnouncementListScreen(navHostController = navHostController)
        }
    }
}