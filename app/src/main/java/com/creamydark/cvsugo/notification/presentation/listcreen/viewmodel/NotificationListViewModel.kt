package com.creamydark.cvsugo.notification.presentation.listcreen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.notification.domain.repository.NotificationRepository
import com.creamydark.cvsugo.notification.presentation.listcreen.event.NotificationListScreenEvents
import com.creamydark.cvsugo.notification.presentation.listcreen.intent.NotificationListScreenIntent
import com.creamydark.cvsugo.notification.presentation.listcreen.state.NotificationListScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NotificationListViewModel @Inject constructor(
    private val repository: NotificationRepository
):ViewModel() {

    var state by mutableStateOf(NotificationListScreenState())
        private set

    private val channel = Channel<NotificationListScreenEvents>()
    val receiver = channel.receiveAsFlow()

    init {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            repository.getAnnouncements().collectLatest {
                result ->
                result.onSuccess {
                    state = state.copy(
                        isLoading = false
                    )
                    state = state.copy(
                        notifications = it
                    )
                }
                result.onFailure {
                    channel.trySend(NotificationListScreenEvents.OnError(it.message.toString()))
                }
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