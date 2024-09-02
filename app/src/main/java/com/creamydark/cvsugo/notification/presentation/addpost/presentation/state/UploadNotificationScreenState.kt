package com.creamydark.cvsugo.notification.presentation.addpost.presentation.state

import com.creamydark.cvsugo.notification.domain.data.NotificationData

data class UploadNotificationScreenState(
    val isLoading: Boolean = false,
    val notification: NotificationData = NotificationData(),
    val error: String? = null
)
