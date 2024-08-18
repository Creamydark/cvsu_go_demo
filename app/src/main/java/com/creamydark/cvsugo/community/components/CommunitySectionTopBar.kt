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
import com.google.firebase.auth.FirebaseUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunitySectionTopBar(modifier: Modifier = Modifier,firebaseUser: FirebaseUser? = null,) {
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
                    model = firebaseUser?.photoUrl,
                    contentScale = ContentScale.Fit,
                    contentDescription = "",
                    clipToBounds = true
                )
            }

        }
    )
}

fun navigateToProfileScreen(navHostController: NavHostController) {
    navHostController.navigate("profile_screen"){
        launchSingleTop = true
    }
}