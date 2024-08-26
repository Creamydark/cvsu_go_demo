package com.creamydark.cvsugo.community.profile.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.creamydark.cvsugo.core.presentation.rootscreen.LocalNavController
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.ProfileRoutesItems
import com.creamydark.cvsugo.community.profile.presentation.profile.state.ProfileScreenState
import com.creamydark.cvsugo.community.profile.presentation.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreenRoot(modifier: Modifier = Modifier,viewmodel: ProfileViewModel = hiltViewModel()) {
    ProfileScreen(state = viewmodel.state)
}


@Composable
private fun ProfileScreen(modifier: Modifier = Modifier,state: ProfileScreenState) {
    Box(modifier = modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.TopCenter){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            val navController = LocalNavController.current

            CircularProfilePicture(
                modifier = Modifier,
                model = state.userData.profilePictureUri,
                name = state.userData.name
                    ?.takeUnless { it.isBlank() }
                    ?.lowercase()
                    ?.replace(Regex("\\b[a-zA-Z]")) { it.value.uppercase() }?:"Unknown",
                email = state.userData.email?:"Unknown",
                onClick = {
                    navigateToChangeProfilePictureScreen(navController)
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Composable
private fun CircularProfilePicture(
    modifier: Modifier = Modifier,
    model: Any? = null,
    name: String = "",
    email: String = "",
    onClick: () -> Unit ={}
) {
    val pictureModifier = modifier
        .clip(CircleShape)
        .sizeIn(minWidth = 68.dp, maxWidth = 68.dp)
        .aspectRatio(1f)
        .background(Color.LightGray, shape = CircleShape)
        .clickable { onClick() }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = model,
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = pictureModifier
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = name,

                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }

    /*AsyncImage(
        modifier = modifier
            .clip(CircleShape)
            .sizeIn(maxHeight = 150.dp, maxWidth = 150.dp, minWidth = 120.dp, minHeight = 120.dp),
        model = model,
        contentDescription = "",
        contentScale = ContentScale.Crop
    )*/
}


fun navigateToChangeProfilePictureScreen(navController: NavController) {
    navController.navigate(ProfileRoutesItems.ChangeProfilePicture.route){
        launchSingleTop = true
    }
}





