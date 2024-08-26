package com.creamydark.cvsugo.community.profile.presentation.changeprofilepicture.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import coil.compose.AsyncImage
import com.creamydark.cvsugo.core.components.BigHeaderMain
import com.creamydark.cvsugo.core.presentation.loading.LoadingScreen
import com.creamydark.cvsugo.community.profile.presentation.changeprofilepicture.presentation.intent.ChangeProfilePictureScreenIntent
import com.creamydark.cvsugo.community.profile.presentation.changeprofilepicture.presentation.state.ChangeProfilePictureScreenState
import com.creamydark.cvsugo.community.profile.presentation.changeprofilepicture.presentation.viewmodel.ChangeProfilePictureScreenViewmodel

@Composable
fun ChangeProfilePictureRootScreen(
    modifier: Modifier = Modifier,
    viewmodel: ChangeProfilePictureScreenViewmodel = hiltViewModel()
) {

    val state = viewmodel.state

    if (state.loading){
        LoadingScreen()
    }else{
        ChangeProfilePictureScreen(
            modifier = modifier,
            state = state,
            intent = viewmodel::onIntent
        )
    }

}


@Composable
private fun ChangeProfilePictureScreen(
    modifier: Modifier = Modifier,
    state: ChangeProfilePictureScreenState = ChangeProfilePictureScreenState(),
    intent: (ChangeProfilePictureScreenIntent) -> Unit = {}
) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        intent(ChangeProfilePictureScreenIntent.SelectImage(uri))
    }



    Box(modifier = modifier
        .fillMaxSize()
        .padding(16.dp)){
        BigHeaderMain(
            modifier = Modifier.align(Alignment.TopStart),
            text = "Change profile picture"
        )
        Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            val pictureModifier = Modifier
                .clip(CircleShape)
                .sizeIn(minWidth = 120.dp, maxWidth = 230.dp)
                .aspectRatio(1f)
                .background(Color.LightGray, shape = CircleShape)
                .clickable {
                    launcher.launch("image/*")
                }
            AsyncImage(
                model = state.selectedImage?: state.userData.profilePictureUri,
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = pictureModifier
            )
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "Click profile to upload new image",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            onClick = { intent(ChangeProfilePictureScreenIntent.Update) },
            enabled = !state.uploading
        ) {
            if(state.uploading){
                CircularProgressIndicator(Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(text = "Update")
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    ChangeProfilePictureScreen()
}