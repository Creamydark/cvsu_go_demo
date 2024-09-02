package com.creamydark.cvsugo.notification.presentation.addpost.presentation.intent

sealed class UploadNotificationScreenIntent {
    object Send: UploadNotificationScreenIntent()
    data class OnTitleChange(val title: String): UploadNotificationScreenIntent()
    data class OnMessageChange(val message: String): UploadNotificationScreenIntent()
}