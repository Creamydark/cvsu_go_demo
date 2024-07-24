package com.creamydark.cvsugo.announcement.presentation.announcements

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.creamydark.cvsugo.announcement.presentation.announcements.viewmodel.AnnouncementViewModel
import com.creamydark.cvsugo.announcement.components.AnnouncementItemCustom
import com.creamydark.cvsugo.announcement.components.BigHeaderMain


@Composable
fun AnnouncementListScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: AnnouncementViewModel = hiltViewModel()
) {
    val announcementlist by viewModel.announcementList.collectAsStateWithLifecycle()
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        item {
            BigHeaderMain(text = "Announcements")
        }
        items(
            items = announcementlist,
            key = { item ->
                // Return a stable + unique key for the item
                item.id
            },
        ){
            item ->
            AnnouncementItemCustom(
                icon = {
                    Icon(imageVector = Icons.Outlined.Notifications, contentDescription = "")
                },
                title = item.title,
                message = item.message
            )
//            HorizontalDivider()
        }
    }
}