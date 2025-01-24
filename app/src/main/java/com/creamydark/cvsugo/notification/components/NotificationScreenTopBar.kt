package com.creamydark.cvsugo.notification.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.creamydark.cvsugo.R
import com.creamydark.cvsugo.core.presentation.rootscreen.LocalNavController
import com.creamydark.cvsugo.core.presentation.rootscreen.navgraphs.NotificationRoutesItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreenTopBar(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    TopAppBar(
        modifier = modifier,
        title = {  },
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
            // Add any additional actions or buttons here
            IconButton(
                onClick = {
                    navController.navigate(NotificationRoutesItems.UploadNotification.route){
                        launchSingleTop = true
                    }
                },
            ) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = "")
            }
        }

    )
}