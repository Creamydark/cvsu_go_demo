package com.creamydark.cvsugo.notification.presentation.listcreen.state

import com.creamydark.cvsugo.notification.domain.data.NotificationData

data class NotificationListScreenState(
    val isLoading: Boolean = false,
    val notifications: List<NotificationData> = emptyList(),
    val error: String? = null
)
