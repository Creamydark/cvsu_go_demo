package com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.SpaceDashboard
import androidx.compose.ui.graphics.vector.ImageVector


sealed class RoutesV2(val route: String) {

    // Home Routes
    data object UniversityGraph: RoutesV2(route = "university_graph")
    data object NOtificationGraph: RoutesV2(route = "notification_graph")
    data object StudentPortalGraph: RoutesV2(route = "student_portal_graph")
    data object StudentProfileGraph: RoutesV2(route = "student_profile_graph")
    data object AuthGraph: RoutesV2(route = "auth_graph")


    data object UniversityHomeScreen: RoutesV2(route = "university_home_screen")
    data object CoursesOfferScreen: RoutesV2(route = "courses_offer_screen")
    data object CoursesOfferDetailScreen: RoutesV2(route = "courses_offer_detail_screen")
    data object AboutUniversityScreen: RoutesV2(route = "about_university_screen")

    // Development Routes
    data object DeveloperScreen: RoutesV2(route = "developer_screen")

    // Account Routes
    data object SignInScreen: RoutesV2(route = "sign_in_screen")

    // Student Portal Routes
    data object StudentDashboardScreen: RoutesV2(route = "student_dashboard_screen")
    data object StudentGradesScreen: RoutesV2(route = "student_grades_screen")

    // Announcement Routes
    data object NotificationScreen: RoutesV2(route = "notification_screen")
    // Profile
    data object ProfileScreen: RoutesV2(route = "profile_screen")
}



enum class StudentPortal(val route: String, val icon: ImageVector, val label: String){
    Dashboard(route = RoutesV2.StudentDashboardScreen.route, icon = Icons.Outlined.SpaceDashboard, label = "Dashboard"),
    Grades(route = RoutesV2.StudentGradesScreen.route, icon = Icons.Outlined.FavoriteBorder, label = "Grades")
}


enum class RootRoutesItems(val route: String, val icon: ImageVector, val label: String) {
    University(route = RoutesV2.UniversityGraph.route, icon = Icons.Outlined.School, label = "University"),
    Notification(route = RoutesV2.NOtificationGraph.route, icon = Icons.Outlined.Notifications, label = "Notifications"),
    Portal(route = RoutesV2.StudentPortalGraph.route, icon = Icons.Outlined.SpaceDashboard, label = "Portal"),
    Profile(route = RoutesV2.StudentProfileGraph.route, icon = Icons.Outlined.Person, label = "Profile")
}


enum class UniversityRoutesItems(val route: String, val icon: ImageVector, val label: String) {
    Home(route = RoutesV2.UniversityHomeScreen.route, icon = Icons.Outlined.School, label = "Home"),
    CoursesOffer(route = RoutesV2.CoursesOfferScreen.route, icon = Icons.Outlined.FavoriteBorder, label = "Courses Offered"),
    AboutUniversity(route = RoutesV2.AboutUniversityScreen.route, icon = Icons.Outlined.Info, label = "About")
}

enum class PortalRoutesItems(val route: String, val icon: ImageVector, val label: String) {
    Dashboard(route = RoutesV2.StudentDashboardScreen.route, icon = Icons.Outlined.SpaceDashboard, label = "Dashboard"),
    Grades(route = RoutesV2.StudentGradesScreen.route, icon = Icons.Outlined.FavoriteBorder, label = "Grades")
}


