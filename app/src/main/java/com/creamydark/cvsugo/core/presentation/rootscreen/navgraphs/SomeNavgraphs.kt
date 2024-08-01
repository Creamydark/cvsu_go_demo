package com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.creamydark.cvsugo.auth.presentation.signin.SignInScreenRoot
import com.creamydark.cvsugo.notification.presentation.listcreen.NotificationListRootScreen
import com.creamydark.cvsugo.portal.presentation.studentportal.dashboard.StudentPortalDashboardRootScreen
import com.creamydark.cvsugo.portal.presentation.studentportal.grades.StudentGradesScreen
import com.creamydark.cvsugo.profile.presentation.profile.ProfileScreenRoot
import com.creamydark.cvsugo.university.presentation.about.AboutUniversityScreen
import com.creamydark.cvsugo.university.presentation.coursesoffered.CoursesOfferedRootScreen
import com.creamydark.cvsugo.university.presentation.main.UniversityHomeScreenRoot

fun NavGraphBuilder.universityNavGraph(navController: NavHostController) {
    navigation(
        route = RootRoutesItems.University.route,
        startDestination = RoutesV2.UniversityHomeScreen.route
    ){
        composable(route = RoutesV2.UniversityHomeScreen.route){
            UniversityHomeScreenRoot(navHostController = navController)
        }

        composable(route = RoutesV2.CoursesOfferScreen.route){
            CoursesOfferedRootScreen()
        }
        composable(route = RoutesV2.AboutUniversityScreen.route){
            AboutUniversityScreen()
        }
    }
}


fun NavGraphBuilder.studentNavGraph(navController: NavHostController) {
    navigation(route = RootRoutesItems.Portal.route, startDestination = StudentPortal.Dashboard.route){
        composable(route = StudentPortal.Dashboard.route){
           StudentPortalDashboardRootScreen()
        }
        composable(route = StudentPortal.Grades.route){
            StudentGradesScreen()
        }
    }
}

fun NavGraphBuilder.profile(navController: NavHostController){
    navigation(
        route = RootRoutesItems.Profile.route,
        startDestination = RoutesV2.ProfileScreen.route
    ){
        composable(route = RoutesV2.ProfileScreen.route){
            ProfileScreenRoot()
        }
    }
}




fun NavGraphBuilder.notification(navController: NavHostController){
    navigation(route = RootRoutesItems.Notification.route, startDestination = RoutesV2.NotificationScreen.route){
        composable(route = RoutesV2.NotificationScreen.route){
            NotificationListRootScreen(navHostController = navController)
        }
    }
}


fun NavGraphBuilder.auth(){
    navigation(route = RoutesV2.AuthGraph.route, startDestination = RoutesV2.SignInScreen.route){
        composable(route = RoutesV2.SignInScreen.route){
            SignInScreenRoot()
        }
    }
}