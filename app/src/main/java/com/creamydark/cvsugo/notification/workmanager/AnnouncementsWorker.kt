package com.creamydark.cvsugo.notification.workmanager

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.creamydark.cvsugo.R
import com.creamydark.cvsugo.core.notificationmanager.NotificationCHIds.ANNOUNCEMENTS_CHANNEL
import com.creamydark.cvsugo.notification.domain.data.NotificationData
import com.creamydark.cvsugo.notification.domain.repository.NotificationRepository
import javax.inject.Inject

@HiltWorker
class AnnouncementsWorker @Inject constructor(
    private val notificationRepository: NotificationRepository,
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        // Fetch and display announcements here
        Log.d("AnnouncementsWorker", "doWork:${Thread.currentThread().name} ")
        notificationRepository.getAnnouncements().collect {
            result ->
            result.onSuccess {
                value: List<NotificationData> ->
                value.forEachIndexed {
                    index, notificationData ->
                    Log.d("getAnnouncements", "doWork:${notificationData.title} ")
                    notifyUser(
                        context = applicationContext,
                        notificationId = index,
                        title = notificationData.title,
                        body = notificationData.message
                    )
                }
            }
            result.onFailure {

            }
        }
        return Result.success()
    }


    private fun notifyUser(
        context: Context,
        notificationId: Int = 0,
        title: String = "",
        body: String = ""
    ){
        // Notification ID and Builder\
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(context, ANNOUNCEMENTS_CHANNEL)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.outline_notifications_24)
            .setPriority(NotificationCompat.PRIORITY_LOW)

        // Start Notification
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}