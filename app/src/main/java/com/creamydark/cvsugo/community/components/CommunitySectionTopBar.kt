package com.creamydark.cvsugo.community.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.creamydark.cvsugo.R
import com.creamydark.cvsugo.core.presentation.rootscreen.LocalNavController
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.RoutesV2
import com.creamydark.cvsugo.googleAuth.domain.dataclass.UserData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunitySectionTopBar(modifier: Modifier = Modifier,userData: UserData,) {
    val navHostController = LocalNavController.current
    TopAppBar(
        modifier = modifier,
        title = { },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Image(
                    painter = painterResource(id = R.drawable.cvsu_clean_logo),
                    contentDescription = "",
                    modifier = Modifier.size(38.dp)
                )
            }
        },
        actions = {
            CreatePostBTN{
                naviagteToCreatePostScreen(navHostController)
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    navigateToProfileScreen(navHostController)
                },
            ) {
                AsyncImage(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.7f),
                            shape = CircleShape
                        )
                        .size(38.dp)

                        .clip(CircleShape),
                    model = userData.profilePictureUri,
                    contentScale = ContentScale.Fit,
                    contentDescription = "",
                    clipToBounds = true
                )
            }

        }
    )
}

private fun navigateToProfileScreen(navHostController: NavHostController) {
    navHostController.navigate(RoutesV2.ProfileGraph.route){
        launchSingleTop = true
    }
}


private fun naviagteToCreatePostScreen(navHostController: NavHostController) {
    navHostController.navigate(RoutesV2.CreatePostScreen.route){
        launchSingleTop = true
    }
}