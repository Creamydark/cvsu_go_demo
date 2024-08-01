package com.creamydark.cvsugo.notification.domain.repository

import com.creamydark.cvsugo.notification.domain.data.NotificationData
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotifications():Flow<List<NotificationData>>
}