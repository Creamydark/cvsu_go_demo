package com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs

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


fun NavGraphBuilder.home(navHostController: NavHostController){
    navigation(route = MainGraph.Home.route, startDestination = HomeScreens.MainScreen.route){
        composable(route = HomeScreens.MainScreen.route){
            HomeMainScreen(navHostController = navHostController)
        }
        composable(route = HomeScreens.CoursesOfferScreen.route){
            CoursesOfferedRootScreen(navHostController = navHostController )
        }
        composable(
            route = HomeScreens.CoursesOfferDetailScreen.route.plus("/{id}"),
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
        composable(route = HomeScreens.AboutUniversityScreen.route){
            AboutRootScreen()
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
    navigation(route = MainGraph.StudentPortal.route, startDestination = StudentPortalScreens.StudentDashboard.route){
        composable(route = StudentPortalScreens.StudentDashboard.route){
            StudentPortalDashboardScreen(navHostController = navHostController)
        }
        composable(route = StudentPortalScreens.StudentGrades.route){
            Text(text = "StudentGrades")
        }
        composable(route = StudentPortalScreens.StudentProfile.route){
            Text(text = "StudentProfile")
        }
    }
}

fun NavGraphBuilder.development(navHostController: NavHostController){
    navigation(route = MainGraph.Development.route, startDestination = DevelopmentScreens.Developer.route){
        composable(route = DevelopmentScreens.Developer.route){
            DeveloperInfoScreen()
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