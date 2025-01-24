package com.creamydark.cvsugo.notification.presentation.addpost.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.notification.domain.repository.NotificationRepository
import com.creamydark.cvsugo.notification.presentation.addpost.presentation.event.UploadNotificationScreenEvents
import com.creamydark.cvsugo.notification.presentation.addpost.presentation.intent.UploadNotificationScreenIntent
import com.creamydark.cvsugo.notification.presentation.addpost.presentation.state.UploadNotificationScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UploadNotificationScreenViewmodel @Inject constructor(
    private val notificationRepository: NotificationRepository
): ViewModel() {

    var state by mutableStateOf(UploadNotificationScreenState())
        private set

    private val channel = Channel<UploadNotificationScreenEvents>()
    val event = channel.receiveAsFlow()
    fun execIntent(onIntent: UploadNotificationScreenIntent){
        when (onIntent) {
            is UploadNotificationScreenIntent.OnMessageChange -> {
                state = state.copy(
                    notification = state.notification.copy(
                        message = onIntent.message
                    )
                )
            }
            is UploadNotificationScreenIntent.OnTitleChange -> {
                state = state.copy(
                    notification = state.notification.copy(
                        title = onIntent.title
                    )
                )
            }
            UploadNotificationScreenIntent.Send -> {
                send()
            }
        }
    }

    private fun send(){
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            notificationRepository.addAnnouncement(state.notification).collectLatest {
                result ->
                result.onSuccess {
                    channel.send(UploadNotificationScreenEvents.OnSuccess("Success"))
                    state = state.copy(
                        isLoading = false
                    )
                }
                result.onFailure {
                    channel.send(UploadNotificationScreenEvents.OnFailed(it.message?:""))
                    state = state.copy(
                        isLoading = false
                    )
                }
            }
        }
    }
}