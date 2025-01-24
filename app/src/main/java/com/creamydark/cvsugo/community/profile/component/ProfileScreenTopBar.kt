package com.creamydark.cvsugo.community.profile.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.creamydark.cvsugo.core.presentation.rootscreen.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenTopBar(modifier: Modifier = Modifier,onSignOut:()->Unit,title:String = "") {
    val navController = LocalNavController.current
    TopAppBar(
        modifier = modifier,
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "")
            }
        },
        actions = {
            // Add any additional actions or buttons here
            IconButton(
                onClick = {
                    onSignOut()
                },
            ) {
                Icon(imageVector = Icons.AutoMirrored.Outlined.ExitToApp, contentDescription = "")
            }
        }

    )
}