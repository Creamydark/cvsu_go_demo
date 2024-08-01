package com.creamydark.cvsugo.profile.presentation.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.creamydark.cvsugo.auth.presentation.letssignin.LetsSignInScreenRoot
import com.creamydark.cvsugo.core.domain.enums.AuthenticationState
import com.creamydark.cvsugo.profile.presentation.profile.state.ProfileScreenState
import com.creamydark.cvsugo.profile.presentation.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreenRoot(modifier: Modifier = Modifier,viewmodel: ProfileViewModel = hiltViewModel()) {
    if (viewmodel.state.authState == AuthenticationState.Unauthenticated){
        LetsSignInScreenRoot()
    }else{
        ProfileScreen(state = viewmodel.state)
    }
}


@Composable
private fun ProfileScreen(modifier: Modifier = Modifier,state: ProfileScreenState) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Maintenance",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Text(text = "Coming Soon")
        }
    }
}