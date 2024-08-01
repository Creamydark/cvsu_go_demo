package com.creamydark.cvsugo.notification.presentation.listcreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.creamydark.cvsugo.core.components.BigHeaderMain
import com.creamydark.cvsugo.core.presentation.loading.LoadingScreen
import com.creamydark.cvsugo.notification.components.NotificationItemCustom
import com.creamydark.cvsugo.notification.presentation.listcreen.intent.NotificationListScreenIntent
import com.creamydark.cvsugo.notification.presentation.listcreen.state.NotificationListScreenState
import com.creamydark.cvsugo.notification.presentation.listcreen.viewmodel.NotificationListViewModel

@Composable
fun NotificationListRootScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: NotificationListViewModel = hiltViewModel()
) {
    val state = viewModel.state

    if (!state.isLoading){
        NotificationListScreen(
            modifier = modifier, state =  state,
            onIntent = {
                    intent ->
                when (intent) {
                    is NotificationListScreenIntent.OnSelectNotification -> {

                    }
                }
            }
        )
    }else{
        LoadingScreen()
    }
}

@Composable
private fun NotificationListScreen(
    modifier: Modifier = Modifier,
    state: NotificationListScreenState,
    onIntent: (NotificationListScreenIntent) -> Unit = {}
) {
    Column(modifier = modifier) {
        LazyColumn(
            modifier = modifier.padding(horizontal = 16.dp)
        ) {
            item {
                BigHeaderMain(text = "Communiqué")
            }
            items(
                items = state.notifications,
                key = { item ->
                    // Return a stable + unique key for the item
                    item.id
                },
            ){
                    item ->
                NotificationItemCustom(
                    icon = {
                        Icon(imageVector = Icons.Outlined.Notifications, contentDescription = "")
                    },
                    title = item.title,
                    message = item.message,
                    onCLicked = {
                        onIntent(NotificationListScreenIntent.OnSelectNotification(item.id))
                    }
                )
            }
        }
    }
}