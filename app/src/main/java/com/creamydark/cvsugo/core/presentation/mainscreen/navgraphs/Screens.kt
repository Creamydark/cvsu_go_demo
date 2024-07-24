package com.creamydark.cvsugo.core.presentation.mainscreen.navgraphs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.DeveloperMode
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.ui.graphics.vector.ImageVector


sealed class HomeScreens(val route:String){
    data object MainScreen: HomeScreens(route = "main_screen")
    data object CoursesOfferScreen: HomeScreens(route = "courses_offer_screen")
    data object CoursesOfferDetailScreen: HomeScreens(route = "courses_offer_detail_screen")
    data object AboutUniversityScreen: HomeScreens(route = "about_university_screen")
}


enum class HomeScreensNavigationItems(
    val route: String,
    val icon: ImageVector,
    val label: String
){
    Home(route = HomeScreens.MainScreen.route, icon = Icons.Outlined.Home, label = "Home"),
    CoursesOffer(route = HomeScreens.CoursesOfferScreen.route, icon = Icons.Outlined.FavoriteBorder, label = "Courses Offered"),
    AboutUniversity(route = HomeScreens.AboutUniversityScreen.route, icon = Icons.Outlined.Info, label = "About University")
}

sealed class DevelopmentScreens(val route:String){
    data object Developer: DevelopmentScreens(route = "developer_screen")
}


sealed class AccountScreens(val route:String){
    data object SignInScreen: AccountScreens(route = "sign_in_screen")
}
sealed class StudentPortalScreens(val route:String){
    data object StudentHome: StudentPortalScreens(route = "student_home_screen")
}
sealed class AnnouncementScreens(val route:String){
    data object ListScreens: AnnouncementScreens(route = "list_screen")
}


sealed class MainGraph(val route:String, val label:String = route,val icon:ImageVector= Icons.Default.Home){
    data object Home: MainGraph(route = "home_screen_graph", label = "Home", icon = Icons.Outlined.Home)
    data object Account: MainGraph(route = "account_screen_graph", label = "Account", icon = Icons.Outlined.Lock)
    data object StudentPortal: MainGraph(route = "student_portal_screen_graph", label = "Student Portal", icon = Icons.Outlined.FavoriteBorder)
    data object Announcement: MainGraph(route = "announcement_screen_graph", label = "Announcement", icon = Icons.Outlined.Notifications)
    data object SplashZero: MainGraph(route = "splash_screen_0")
    data object Development: MainGraph(route = "development_screen_graph", label = "Development", icon = Icons.Outlined.DeveloperMode)
}