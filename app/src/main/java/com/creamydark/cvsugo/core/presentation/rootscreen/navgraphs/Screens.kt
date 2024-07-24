package com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.DeveloperMode
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.FiberSmartRecord
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Routes(val route: String) {
    // Home Routes
    data object MainScreen: Routes(route = "main_screen")
    data object CoursesOfferScreen: Routes(route = "courses_offer_screen")
    data object CoursesOfferDetailScreen: Routes(route = "courses_offer_detail_screen")
    data object AboutUniversityScreen: Routes(route = "about_university_screen")

    // Development Routes
    data object DeveloperScreen: Routes(route = "developer_screen")

    // Account Routes
    data object SignInScreen: Routes(route = "sign_in_screen")

    // Student Portal Routes
    data object StudentDashboardScreen: Routes(route = "student_dashboard_screen")
    data object StudentGradesScreen: Routes(route = "student_grades_screen")
    data object StudentProfileScreen: Routes(route = "student_profile_screen")

    // Announcement Routes
    data object AnnouncementListScreen: Routes(route = "announcement_list_screen")
}

enum class HomeNavigationItems(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    Home(route = Routes.MainScreen.route, icon = Icons.Outlined.Home, label = "Home"),
    CoursesOffer(route = Routes.CoursesOfferScreen.route, icon = Icons.Outlined.FavoriteBorder, label = "Courses Offered"),
    AboutUniversity(route = Routes.AboutUniversityScreen.route, icon = Icons.Outlined.Info, label = "About University")
}

enum class StudentPortalNavigationItems(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    Dashboard(route = Routes.StudentDashboardScreen.route, icon = Icons.Outlined.Dashboard, label = "Dashboard"),
    Grades(route = Routes.StudentGradesScreen.route, icon = Icons.Outlined.FiberSmartRecord, label = "Grades"),
    Profile(route = Routes.StudentProfileScreen.route, icon = Icons.Outlined.Person, label = "Profile")
}

sealed class MainGraph(val route: String, val label: String = route, val icon: ImageVector = Icons.Default.Home) {
    data object Home: MainGraph(route = "home_screen_graph", label = "Home", icon = Icons.Outlined.Home)
    data object Account: MainGraph(route = "account_screen_graph", label = "Account", icon = Icons.Outlined.Lock)
    data object StudentPortal: MainGraph(route = "student_portal_screen_graph", label = "Student Portal", icon = Icons.Outlined.FavoriteBorder)
    data object Announcement: MainGraph(route = "announcement_screen_graph", label = "Announcement", icon = Icons.Outlined.Notifications)
    data object SplashZero: MainGraph(route = "splash_screen_0")
    data object Development: MainGraph(route = "development_screen_graph", label = "Development", icon = Icons.Outlined.DeveloperMode)
}
