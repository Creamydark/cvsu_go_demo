package com.creamydark.cvsugo.notification.data.repository

import com.creamydark.cvsugo.notification.domain.data.NotificationData
import com.creamydark.cvsugo.notification.domain.repository.NotificationRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.UUID
import javax.inject.Inject


class NotificationRepositoryImpl @Inject constructor(): NotificationRepository {
    override fun getNotifications(): Flow<List<NotificationData>> {
        return callbackFlow {
//            delay(1000)
            val list = listOf(
                NotificationData(
                    id = UUID.randomUUID().toString(),
                    title = "CvSU AchievesHigher Education Accreditation!",
                    message = "Congratulations! CvSU has been granted Level IV accreditation by [Accreditation Body]. This reflects our commitment to quality education."
                ),
                NotificationData(
                    id = UUID.randomUUID().toString(),
                    title = "New Online Enrollment System Launched",
                    message= "Enroll for the upcoming semester with ease using our new online enrollment portal. Sign in for more details and to start your enrollment process."
                ),
                NotificationData(
                    id = UUID.randomUUID().toString(),
                    title = "Upcoming Career Fair: Get Ready to Network!",
                    message = "Don't miss the annual CvSU Career Fair on 00/00/0000. Connect with top employers and explore exciting career opportunities. Prepare your resumes and dress to impress!"
                )
            )
            trySend(list)
            close()
            awaitClose {  }
        }
    }
}