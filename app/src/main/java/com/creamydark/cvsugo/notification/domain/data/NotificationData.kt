package com.creamydark.cvsugo.notification.domain.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class NotificationData(
    @DocumentId val notificationId: String = "",
    @ServerTimestamp val theTimestamp : Date = Date(),
    val title: String = "",
    val message: String = "",
    val photoUrl:String = "",
    val userID:String = "",
)
