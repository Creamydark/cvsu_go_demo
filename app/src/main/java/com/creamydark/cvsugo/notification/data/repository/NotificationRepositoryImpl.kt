package com.creamydark.cvsugo.notification.data.repository

import com.creamydark.cvsugo.core.util.ANNOUNCEMENTS
import com.creamydark.cvsugo.notification.data.datasource.AnnouncementFirestoreDataSource
import com.creamydark.cvsugo.notification.domain.data.NotificationData
import com.creamydark.cvsugo.notification.domain.repository.NotificationRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject


class NotificationRepositoryImpl @Inject constructor(): NotificationRepository {

    val firestore = Firebase.firestore
    val auth = Firebase.auth

    override suspend fun getNotifications(): Flow<List<NotificationData>> {
        return callbackFlow {
//            delay(1000)
            val list = listOf(
                NotificationData(
                    notificationId = UUID.randomUUID().toString(),
                    title = "CvSU AchievesHigher Education Accreditation!",
                    message = "Congratulations! CvSU has been granted Level IV accreditation by [Accreditation Body]. This reflects our commitment to quality education."
                ),
                NotificationData(
                    notificationId = UUID.randomUUID().toString(),
                    title = "New Online Enrollment System Launched",
                    message= "Enroll for the upcoming semester with ease using our new online enrollment portal. Sign in for more details and to start your enrollment process."
                ),
                NotificationData(
                    notificationId = UUID.randomUUID().toString(),
                    title = "Upcoming Career Fair: Get Ready to Network!",
                    message = "Don't miss the annual CvSU Career Fair on 00/00/0000. Connect with top employers and explore exciting career opportunities. Prepare your resumes and dress to impress!"
                )
            )
            trySend(list)
            close()
            awaitClose {  }
        }
    }

    override suspend fun getAnnouncements(): Flow<Result<List<NotificationData>>> {
        return AnnouncementFirestoreDataSource().getAnnouncements()
    }

    override suspend fun addAnnouncement(notificationData: NotificationData): Flow<Result<String>> = callbackFlow {

        val job = launch {
            try {
                val currentUser = auth.currentUser?.uid?:""
                if (currentUser.isEmpty()) {
                    trySend(Result.failure(Exception("User not authenticated")))
                }
                val task = firestore.collection(ANNOUNCEMENTS).add(notificationData.copy(userID = currentUser)).await()
                trySend(Result.success(task.id))
            }catch (e:Exception){
                trySend(Result.failure(e))
            }finally {
                close()
            }
        }
        awaitClose { job.cancel() }
    }

    override suspend fun deleteNotification(notificationData: NotificationData): Flow<Result<String>> = callbackFlow {
        val job = launch {
            try {
                firestore.collection(ANNOUNCEMENTS).document(notificationData.notificationId).delete().await()
                trySend(Result.success("Deleted"))
            }catch (e:Exception){
                trySend(Result.failure(e))
            }finally {
                close()
            }
        }
        awaitClose { job.cancel()  }
    }


}