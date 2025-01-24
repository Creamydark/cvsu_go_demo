package com.creamydark.cvsugo.notification.data.datasource

import com.creamydark.cvsugo.core.util.ANNOUNCEMENTS
import com.creamydark.cvsugo.notification.domain.data.NotificationData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class AnnouncementFirestoreDataSource {
    val firestore = Firebase.firestore
       suspend fun getAnnouncements():Flow<Result<List<NotificationData>>>{
            return callbackFlow {
                val job = launch {
                    firestore.collection(ANNOUNCEMENTS).addSnapshotListener { value, error ->
                        val data = value?.toObjects(NotificationData::class.java)?: emptyList()
                        if (error != null){
                            trySend(Result.failure(error))
                        }
                        trySend(Result.success(data))
                    }
                }
                awaitClose { job.cancel() }
            }
        }
}