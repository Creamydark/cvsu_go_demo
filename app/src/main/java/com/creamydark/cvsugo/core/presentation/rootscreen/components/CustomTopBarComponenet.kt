package com.creamydark.cvsugo.core.presentation.rootscreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarCustomComponent(modifier: Modifier = Modifier) {
    val navHostController = LocalNavController.current
    TopAppBar(
        modifier = modifier,
        title = { /*TODO*/ },
        navigationIcon = {
            IconButton(
                onClick = {
                    navHostController.navigateUp()
                },
            ) {
                Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "")
            }
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarCustomComponent0(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        title = { /*TODO*/ },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Image(
                    painter = painterResource(id = R.drawable.cvsu_clean_logo),
                    contentDescription = "",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    )
}