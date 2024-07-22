package com.creamydark.cvsugo.presentation.navgraphs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class HomeScreens(val route:String){
    data object RootScreen:HomeScreens(route = "home_screen")
}

sealed class CoursesScreens(val route:String){
    data object RootScreen:CoursesScreens(route = "courses_screen")
    data object CourseDetailScreen:CoursesScreens(route = "course_detail_screen")
}

sealed class AboutScreens(val route:String){
    data object RootScreen:AboutScreens(route = "About_screen")
}

sealed class AppInfoScreens(val route:String){
    data object RootScreen:AppInfoScreens(route = "app_info_screen")
}

sealed class AccountScreens(val route:String){
    data object SignInScreen:AccountScreens(route = "sign_in_screen")
}
sealed class StudentPortalScreens(val route:String){
    data object StudentHome:StudentPortalScreens(route = "student_home_screen")
}
sealed class AnnouncementScreens(val route:String){
    data object ListScreens:AnnouncementScreens(route = "list_screen")
}

sealed class SplashScreens(val route:String){
    data object SplashZero:SplashScreens(route = "splash_screen_0")
}

sealed class MainGraph(val route:String, val label:String = route,val icon:ImageVector= Icons.Default.Home){
    data object Home:MainGraph(route = "home_screen_graph", label = "Home", icon = Icons.Outlined.Home)
    data object Courses:MainGraph(route = "courses_screen_graph", label = "Courses Offered", icon = Icons.Outlined.LocalOffer)
    data object About:MainGraph(route = "about_screen_graph", label = "About", icon = Icons.Outlined.Info)
    data object AppInfo:MainGraph(route = "app_info_screen_graph", label = "App Info", icon = Icons.Outlined.Person)
    data object Account:MainGraph(route = "account_screen_graph", label = "Account", icon = Icons.Outlined.Lock)
    data object StudentPortal:MainGraph(route = "student_portal_screen_graph", label = "Student Portal", icon = Icons.Outlined.FavoriteBorder)
    data object Announcement:MainGraph(route = "announcement_screen_graph", label = "Announcement", icon = Icons.Outlined.Notifications)
}