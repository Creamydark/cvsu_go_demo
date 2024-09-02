package com.creamydark.cvsugo.notification.presentation.listcreen.event

sealed class NotificationListScreenEvents {
    data class OnError(val message: String) : NotificationListScreenEvents()
}