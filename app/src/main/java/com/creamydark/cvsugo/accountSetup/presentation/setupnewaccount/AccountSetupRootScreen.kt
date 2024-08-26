package com.creamydark.cvsugo.accountSetup.presentation.setupnewaccount

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.creamydark.cvsugo.accountSetup.presentation.setupnewaccount.intent.AccountSetupScreenIntent
import com.creamydark.cvsugo.accountSetup.presentation.setupnewaccount.state.AccountSetupScreenState
import com.creamydark.cvsugo.accountSetup.presentation.setupnewaccount.viewmodel.AccountSetupViewmodel


@Composable
fun AccountSetupRootScreen(viewmodel: AccountSetupViewmodel = hiltViewModel()) {
    AccountSetupScreen(state = viewmodel.state, onIntent = viewmodel::onIntent)
}


@Composable
private fun AccountSetupScreen(
    modifier: Modifier = Modifier,
    state: AccountSetupScreenState = AccountSetupScreenState(),
    onIntent: (AccountSetupScreenIntent) -> Unit = {}
) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        onIntent(AccountSetupScreenIntent.OnProfilePictureChanged(uri))
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        contentAlignment = Alignment.Center) {

        /*Text(
            modifier = Modifier.padding(vertical = 22.dp).align(TopCenter),
            text = "Account Setup",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 26.sp,
            lineHeight = 42.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )*/

        Column(
            modifier = modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ProfilePicture(imageUri = state.selectedImageUri?:state.profilePictureUri) {
                // Handle profile picture selection logic here
                // For example, you can launch an image picker here
                launcher.launch("image/*")
            }
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "Click profile to upload new image",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            UsernameInputField(
                username = state.username,
                supportingText = state.error,
                onUsernameChange = {
                    onIntent(AccountSetupScreenIntent.OnUsernameChanged(it))
                }
            )

            Spacer(modifier = Modifier.height(24.dp))


        }
        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            onClick = {
                // Handle save or continue action
                onIntent(AccountSetupScreenIntent.OnSaveClicked)
            },
            enabled = !state.loading
        ) {
            Row {
                if(state.loading){
                    CircularProgressIndicator(Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(text = "Continue")
            }
        }
    }
}

@Composable
private fun ProfilePicture(imageUri: Any?, onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .size(190.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageUri,
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(190.dp)
        )
    }
}

@Composable
private fun UsernameInputField(
    username: String,
    onUsernameChange: (String) -> Unit,
    supportingText: String?
) {
    OutlinedTextField(
        value = username,
        onValueChange = onUsernameChange,
        label = { Text("Username") },
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = "Enter your username")
        },
        supportingText = {
            if (supportingText != null) {
                Text(text = supportingText, color = Color.Red.copy(alpha = 0.7f), fontWeight = FontWeight.Medium)
            }
        }
    )
}