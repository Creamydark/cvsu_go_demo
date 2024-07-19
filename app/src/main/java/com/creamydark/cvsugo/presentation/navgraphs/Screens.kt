package com.creamydark.cvsugo.presentation.navgraphs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
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

sealed class MainGraph(val route:String, val label:String = route,val icon:ImageVector= Icons.Default.Home){
    data object Home:MainGraph(route = "home_screen_graph", label = "Home", icon = Icons.Outlined.Home)
    data object Courses:MainGraph(route = "courses_screen_graph", label = "Courses Offered", icon = Icons.Outlined.FavoriteBorder)
    data object About:MainGraph(route = "about_screen_graph", label = "About", icon = Icons.Outlined.Info)
    data object AppInfo:MainGraph(route = "app_info_screen_graph", label = "App Info", icon = Icons.Outlined.Build)

}