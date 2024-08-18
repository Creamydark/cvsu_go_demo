package com.creamydark.cvsugo.profile.presentation.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.creamydark.cvsugo.profile.presentation.profile.state.ProfileScreenState
import com.creamydark.cvsugo.profile.presentation.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreenRoot(modifier: Modifier = Modifier,viewmodel: ProfileViewModel = hiltViewModel()) {
    ProfileScreen(state = viewmodel.state)
}


@Composable
private fun ProfileScreen(modifier: Modifier = Modifier,state: ProfileScreenState) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            CircularProfilePicture( model = state.user?.photoUrl)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = state.user?.displayName
                    ?.takeUnless { it.isBlank() }
                    ?.lowercase()
                    ?.replace(Regex("\\b[a-zA-Z]")) { it.value.uppercase() }
                    ?: "Unknown",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
//                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )


//            Text(text = "Coming Soon")
        }
    }
}

@Composable
private fun CircularProfilePicture(
    modifier: Modifier = Modifier,
    model: Any? = null
) {
    AsyncImage(
        modifier = modifier
            .clip(CircleShape)
            .sizeIn(maxHeight = 150.dp, maxWidth = 150.dp, minWidth = 120.dp, minHeight = 120.dp),
        model = model,
        contentDescription = "",
        contentScale = ContentScale.Fit
    )
}


@Preview
@Composable
private fun Prev() {

}