package com.creamydark.cvsugo

import AnnouncementsWorker
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.creamydark.cvsugo.core.notificationmanager.NotificationCHIds.ANNOUNCEMENTS_CHANNEL
import com.creamydark.cvsugo.core.notificationmanager.NotificationCHIds.GREETINGS_CHANNEL
import com.creamydark.cvsugo.core.notificationmanager.NotificationCHIds.UPLOAD_POST_CHANNEL
import com.creamydark.cvsugo.core.workmanager.GreetingtWorkManager
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class MyApp:Application() {



    override fun onCreate() {
        super.onCreate()


        createGreetingsNotificationChannel(applicationContext)

        createUploadPostNotificationChannel(applicationContext)

        createAnnouncementsNotificationChannel(applicationContext)

        enqueueAnnouncementsWorkManager(applicationContext)

        enqueueGreetingWorkManager(applicationContext)


    }



}


private fun enqueueAnnouncementsWorkManager(context: Context){

    val announcementWorkRequest = PeriodicWorkRequestBuilder<AnnouncementsWorker>(
        repeatInterval = 3,
        repeatIntervalTimeUnit = TimeUnit.HOURS
    )
        .setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)  // Only run when connected to the network
                .build()
        )
        .build()
    WorkManager.getInstance(context).enqueue(announcementWorkRequest)
}
private fun enqueueGreetingWorkManager(context: Context){


    val workRequest = PeriodicWorkRequestBuilder<GreetingtWorkManager>(
        repeatInterval = 3,
        repeatIntervalTimeUnit = TimeUnit.HOURS
    ).build()
    val workManager = WorkManager.getInstance(context)
    workManager.enqueue(workRequest)

}

private fun createGreetingsNotificationChannel(context: Context) {
    val channel = NotificationChannel(
        /* id = */ GREETINGS_CHANNEL,
        /* name = */ "University Greetings",
        /* importance = */ NotificationManager.IMPORTANCE_DEFAULT
    )
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}


private fun createUploadPostNotificationChannel(context: Context) {
    val channelId = UPLOAD_POST_CHANNEL
    val channelName = "Upload Notifications"
    val channelDescription = "Notifications for image upload progress"
    val importance = NotificationManager.IMPORTANCE_LOW
    val channel = NotificationChannel(channelId, channelName, importance).apply {
        description = channelDescription
    }
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}

private fun createAnnouncementsNotificationChannel(context: Context) {
    val channelId = ANNOUNCEMENTS_CHANNEL
    val channelName = "Announcements"
    val channelDescription = "Announcements from the university"
    val importance = NotificationManager.IMPORTANCE_LOW
    val channel = NotificationChannel(channelId, channelName, importance).apply {
        description = channelDescription
    }
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}
