package com.creamydark.cvsugo.notification.presentation.listcreen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.notification.domain.repository.NotificationRepository
import com.creamydark.cvsugo.notification.presentation.listcreen.intent.NotificationListScreenIntent
import com.creamydark.cvsugo.notification.presentation.listcreen.state.NotificationListScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NotificationListViewModel @Inject constructor(
    private val repository: NotificationRepository
):ViewModel() {
    var state by mutableStateOf(NotificationListScreenState())
        private set


    init {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            repository.getNotifications().collectLatest {
                list ->
                state = state.copy(notifications = list, isLoading = false)
            }
        }
    }

    fun execIntent(onIntent: NotificationListScreenIntent){
        when (onIntent) {
            is NotificationListScreenIntent.OnSelectNotification -> {

            }
        }
    }
}