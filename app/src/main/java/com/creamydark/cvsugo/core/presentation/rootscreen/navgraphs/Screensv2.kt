package com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.SpaceDashboard
import androidx.compose.ui.graphics.vector.ImageVector


sealed class RoutesV2(val route: String) {

    // Graphs
    data object UniversityGraph: RoutesV2(route = "university_graph")
    data object NOtificationGraph: RoutesV2(route = "notification_graph")
    data object StudentPortalGraph: RoutesV2(route = "student_portal_graph")
    data object AuthGraph: RoutesV2(route = "auth_graph")
    data object CommunityGraph: RoutesV2(route = "community_graph")
    data object GoogleAuthGraph:RoutesV2(route = "google_auth_in_graph")
    data object ProfileGraph:RoutesV2(route = "profile_graph")


    // Screen Routes
    object UniversityHomeScreen : RoutesV2("university_home_screen")
    object CoursesOfferScreen : RoutesV2("courses_offer_screen")
    object CoursesOfferDetailScreen : RoutesV2("courses_offer_detail_screen")
    object AboutUniversityScreen : RoutesV2("about_university_screen")
    object DeveloperScreen : RoutesV2("developer_screen")
    object SignInScreen : RoutesV2("sign_in_screen")
    object StudentDashboardScreen : RoutesV2("student_dashboard_screen")
    object StudentGradesScreen : RoutesV2("student_grades_screen")
    object NotificationScreen : RoutesV2("notification_screen")
    object ProfileScreen : RoutesV2("profile_screen")
    object FeedListScreen : RoutesV2("feed_list_screen")
    object GoogleSignInScreen : RoutesV2("google_sign_in_screen")
    object GoogleSignUpScreen : RoutesV2("google_sign_up_screen")
    object CreatePostScreen : RoutesV2("create_post_screen")
    object EditPostScreen : RoutesV2("edit_post_screen")
    object AccountSetupScreen : RoutesV2("account_setup_screen")
    object LoadingScreen : RoutesV2("loading_screen")
    object PostDetailScreen : RoutesV2("post_detail_screen")
    object ChangeProfilePictureScreen : RoutesV2("change_profile_picture_screen")
    object UploadNotificationScreen : RoutesV2("upload_notification_screen")
}



enum class SignInRoutesItems(val route: String, val label: String) {
    SignIn(route = RoutesV2.SignInScreen.route, label = "Sign In"),
    SignUp(route = RoutesV2.GoogleSignUpScreen.route, label = "Sign Up")
}


enum class StudentPortal(val route: String, val icon: ImageVector, val label: String){
    Dashboard(route = RoutesV2.StudentDashboardScreen.route, icon = Icons.Outlined.SpaceDashboard, label = "Dashboard"),
    Grades(route = RoutesV2.StudentGradesScreen.route, icon = Icons.Outlined.FavoriteBorder, label = "Grades")
}


enum class RootRoutesItems(val route: String, val icon: ImageVector, val label: String) {
    University(route = RoutesV2.UniversityGraph.route, icon = Icons.Outlined.School, label = "University"),
    Notification(route = RoutesV2.NOtificationGraph.route, icon = Icons.Outlined.Notifications, label = "Notifications"),
    Portal(route = RoutesV2.StudentPortalGraph.route, icon = Icons.Outlined.SpaceDashboard, label = "Portal"),
    //Profile(route = RoutesV2.StudentProfileGraph.route, icon = Icons.Outlined.Person, label = "Profile")
    Community(route = RoutesV2.CommunityGraph.route, icon = Icons.Outlined.People, label = "Community")
}


enum class UniversityRoutesItems(val route: String, val icon: ImageVector, val label: String) {
    Home(route = RoutesV2.UniversityHomeScreen.route, icon = Icons.Outlined.School, label = "Home"),
    CoursesOffer(route = RoutesV2.CoursesOfferScreen.route, icon = Icons.Outlined.FavoriteBorder, label = "Courses Offered"),
    CoursesDetail(route = RoutesV2.CoursesOfferDetailScreen.route, icon = Icons.Outlined.Info, label = "Detail"),
    AboutUniversity(route = RoutesV2.AboutUniversityScreen.route, icon = Icons.Outlined.Info, label = "About")
}


enum class NotificationRoutesItems(val route: String, val icon: ImageVector, val label: String){

    Notification(route = RoutesV2.NotificationScreen.route , icon = Icons.Outlined.Notifications, label = "Notifications"),
    UploadNotification(route = RoutesV2.UploadNotificationScreen.route , icon = Icons.Outlined.Add, label = "Upload Notification")

}

enum class PortalRoutesItems(val route: String, val icon: ImageVector, val label: String) {
    Dashboard(route = RoutesV2.StudentDashboardScreen.route, icon = Icons.Outlined.SpaceDashboard, label = "Dashboard"),
    Grades(route = RoutesV2.StudentGradesScreen.route, icon = Icons.Outlined.FavoriteBorder, label = "Grades")
}


enum class CommunityRoutesItems(val route: String, val icon: ImageVector, val label: String) {
    FeedList(route = RoutesV2.FeedListScreen.route, icon = Icons.Outlined.SpaceDashboard, label = "Feed"),
    PostDetail(route = RoutesV2.PostDetailScreen.route, icon = Icons.Outlined.FavoriteBorder, label = "Post Detail"),
    CreatePost(route = RoutesV2.CreatePostScreen.route, icon = Icons.Outlined.Info, label = "Create Post"),
    EditPost(route = RoutesV2.EditPostScreen.route, icon = Icons.Outlined.Info, label = "Edit Post")
}


enum class ProfileRoutesItems(val route: String, val icon: ImageVector, val label: String) {
    Profile(route = RoutesV2.ProfileScreen.route, icon = Icons.Outlined.SpaceDashboard, label = "Profile"),
    ChangeProfilePicture(route = RoutesV2.ChangeProfilePictureScreen.route, icon = Icons.Outlined.FavoriteBorder, label = "Change Profile Picture")
}
