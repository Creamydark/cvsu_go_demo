package com.creamydark.cvsugo.notification.domain.repository

import com.creamydark.cvsugo.notification.domain.data.NotificationData
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun getNotifications():Flow<List<NotificationData>>

    suspend fun getAnnouncements(): Flow<Result<List<NotificationData>>>

    suspend fun addAnnouncement(notificationData: NotificationData):Flow<Result<String>>

    suspend fun deleteNotification(notificationData: NotificationData):Flow<Result<String>>
}