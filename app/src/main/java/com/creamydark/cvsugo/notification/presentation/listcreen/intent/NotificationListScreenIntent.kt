package com.creamydark.cvsugo.notification.presentation.listcreen.intent

sealed class NotificationListScreenIntent {
    data class OnSelectNotification(val id: String) : NotificationListScreenIntent()
}