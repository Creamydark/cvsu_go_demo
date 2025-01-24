package com.creamydark.cvsugo.community.profile.presentation.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.creamydark.cvsugo.community.profile.component.ProfileImage
import com.creamydark.cvsugo.core.presentation.rootscreen.LocalNavController
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.ProfileRoutesItems
import com.creamydark.cvsugo.community.profile.presentation.profile.state.ProfileScreenState
import com.creamydark.cvsugo.community.profile.presentation.profile.viewmodel.ProfileViewModel
import com.creamydark.cvsugo.googleAuth.domain.dataclass.UserData

@Composable
fun ProfileScreenRoot(modifier: Modifier = Modifier,viewmodel: ProfileViewModel = hiltViewModel()) {
    ProfileScreen(state = viewmodel.state)
}


@Composable
private fun ProfileScreen(modifier: Modifier = Modifier,state: ProfileScreenState) {
    Box(modifier = modifier
        .fillMaxSize()
        .padding(16.dp), contentAlignment = Alignment.TopCenter){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            val navController = LocalNavController.current

            state.user?.let {

                for (profile in it.providerData) {
                    // Id of the provider (ex: google.com)

                    val providerId = profile.providerId
                    Log.d("ProfileScreen", "ProfileScreen: $providerId")

                    // UID specific to the provider
                    val uid = profile.uid

                    // Name, email address, and profile photo Url
                    val name = profile.displayName
                    val email = profile.email
                    val photoUrl = profile.photoUrl

                    if (providerId == "google.com")
                    ProfileSection (
                        image = state.userData.profilePictureUri,
                        profileModel = state.userData.copy(
                            name = name,
                            email = email
                        )
                    ){
                        navigateToChangeProfilePictureScreen(navController)
                    }

                }

            }


            /*CircularProfilePicture(
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
            )*/

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PrevProfileSection() {
    ProfileSection(
        image = null,
        profileModel = UserData(
            name = "Marc Luis Segunto",
            email = "marcluis1187@gmail.com"
        )
    ){

    }
}



@Composable
private fun ProfileSection(modifier: Modifier=Modifier,image:Any?,profileModel:UserData,onClick: () -> Unit){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val pictureModifier = Modifier
            .clip(CircleShape)
            .width(140.dp)
            .aspectRatio(1f)
            .background(Color.LightGray, shape = CircleShape)
            .clickable { onClick() }
        AsyncImage(
            model = image,
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = pictureModifier
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Text(
            text = profileModel.name
                ?.takeUnless { it.isBlank() }
                ?.lowercase()
                ?.replace(Regex("\\b[a-zA-Z]")) { it.value.uppercase() }?:"Unknown",

            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = profileModel.email?:"",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
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
}


fun navigateToChangeProfilePictureScreen(navController: NavController) {
    navController.navigate(ProfileRoutesItems.ChangeProfilePicture.route){
        launchSingleTop = true
    }
}





