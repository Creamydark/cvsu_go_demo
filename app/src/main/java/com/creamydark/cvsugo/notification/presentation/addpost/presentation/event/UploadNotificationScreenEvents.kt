package com.creamydark.cvsugo.notification.presentation.addpost.presentation.event

sealed class UploadNotificationScreenEvents {
    data class OnFailed(val message: String) : UploadNotificationScreenEvents()
    data class OnSuccess(val message: String) : UploadNotificationScreenEvents()
}