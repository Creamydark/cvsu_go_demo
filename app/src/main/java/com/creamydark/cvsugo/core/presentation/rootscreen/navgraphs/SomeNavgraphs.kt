package com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs

import FeedPostDetailRootScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.creamydark.cvsugo.auth.presentation.signin.SignInScreenRoot
import com.creamydark.cvsugo.community.feed.presentation.createpost.presentation.CreatePostRootScreen
import com.creamydark.cvsugo.community.feed.presentation.editpost.presentation.EditPostRootScreen
import com.creamydark.cvsugo.community.feed.presentation.editpost.viewmodel.EditPostScreenViewmodel
import com.creamydark.cvsugo.community.feed.presentation.feedlist.FeedListRootScreen
import com.creamydark.cvsugo.community.feed.presentation.postdetail.presentation.viewmodel.FeedPostDetailScreenViewmodel
import com.creamydark.cvsugo.community.profile.presentation.changeprofilepicture.presentation.ChangeProfilePictureRootScreen
import com.creamydark.cvsugo.community.profile.presentation.profile.ProfileScreenRoot
import com.creamydark.cvsugo.googleAuth.presentation.oneClickSignIn.OneClickSignInRootScreen
import com.creamydark.cvsugo.googleAuth.presentation.signup.SignUpRootScreen
import com.creamydark.cvsugo.notification.presentation.listcreen.NotificationListRootScreen
import com.creamydark.cvsugo.portal.presentation.studentportal.dashboard.StudentPortalDashboardRootScreen
import com.creamydark.cvsugo.portal.presentation.studentportal.grades.StudentGradesScreen
import com.creamydark.cvsugo.university.presentation.about.AboutUniversityScreen
import com.creamydark.cvsugo.university.presentation.coursesoffered.CourseDetailScreen
import com.creamydark.cvsugo.university.presentation.coursesoffered.CoursesOfferedRootScreen
import com.creamydark.cvsugo.university.presentation.coursesoffered.viewmodel.CourseDetailViewModel
import com.creamydark.cvsugo.university.presentation.main.UniversityHomeScreenRoot

fun NavGraphBuilder.universityNavGraph(navController: NavHostController) {
    navigation(
        route = RootRoutesItems.University.route,
        startDestination = UniversityRoutesItems.Home.route
    ){
        composable(route = UniversityRoutesItems.Home.route){
            UniversityHomeScreenRoot(navHostController = navController)
        }
        composable(route = UniversityRoutesItems.CoursesDetail.route.plus("/{id}"),
            listOf(navArgument("id") { type = NavType.StringType })
        ){
                navBackStackEntry->
            val c: CourseDetailViewModel = hiltViewModel(navBackStackEntry)
            CourseDetailScreen( viewModel = c)
        }

        composable(route = UniversityRoutesItems.CoursesOffer.route){
            CoursesOfferedRootScreen()
        }
        composable(route = UniversityRoutesItems.AboutUniversity.route){
            AboutUniversityScreen()
        }
    }
}


fun NavGraphBuilder.student() {
    navigation(route = RootRoutesItems.Portal.route, startDestination = StudentPortal.Dashboard.route){
        composable(route = StudentPortal.Dashboard.route){
           StudentPortalDashboardRootScreen()
        }
        composable(route = StudentPortal.Grades.route){
            StudentGradesScreen()
        }
    }
}

fun NavGraphBuilder.community(){
    navigation(route = RootRoutesItems.Community.route, startDestination = CommunityRoutesItems.FeedList.route){
        composable(route = CommunityRoutesItems.FeedList.route){
            FeedListRootScreen()
        }
        composable(
            route = CommunityRoutesItems.PostDetail.route.plus("/{postId}"),
            listOf(navArgument("postId") { type = NavType.StringType }),
        ){
            navBackStackEntry ->
            val viewmodel : FeedPostDetailScreenViewmodel = hiltViewModel(navBackStackEntry)
            FeedPostDetailRootScreen(viewModel = viewmodel)
        }
        composable(route = CommunityRoutesItems.CreatePost.route){
            CreatePostRootScreen()
        }
        composable(
            route = CommunityRoutesItems.EditPost.route.plus("/{postId}"),
            listOf(navArgument("postId") { type = NavType.StringType })
        ){
            navBackStackEntry ->
            val viewmodel : EditPostScreenViewmodel = hiltViewModel(navBackStackEntry)
            EditPostRootScreen(viewmodel = viewmodel)
        }
    }
}




fun NavGraphBuilder.notification(navController: NavHostController){
    navigation(route = RootRoutesItems.Notification.route, startDestination = NotificationRoutesItems.Notification.route){
        composable(route = NotificationRoutesItems.Notification.route){
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

fun NavGraphBuilder.googleAuth(){
    navigation(route = RoutesV2.GoogleAuthGraph.route, startDestination = SignInRoutesItems.SignIn.route){
        composable(route = SignInRoutesItems.SignIn.route){
//            GoogleSignRootScreen()
            OneClickSignInRootScreen()
        }
        composable(route = SignInRoutesItems.SignUp.route){
            SignUpRootScreen()
        }
    }
}


fun NavGraphBuilder.profile(){
    navigation(route = RoutesV2.ProfileGraph.route, startDestination = ProfileRoutesItems.Profile.route){
        composable(route = ProfileRoutesItems.Profile.route){
            ProfileScreenRoot()
        }
        composable(route = ProfileRoutesItems.ChangeProfilePicture.route){
            ChangeProfilePictureRootScreen()
        }
    }
}