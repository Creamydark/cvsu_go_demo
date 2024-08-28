package com.creamydark.cvsugo

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.creamydark.cvsugo.core.notificationmanager.NotificationCHIds.greetingsChannel
import com.creamydark.cvsugo.core.notificationmanager.NotificationCHIds.uploadPostChannel
import com.creamydark.cvsugo.core.workmanager.GreetingtWorkManager
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()


        createGreetingsNotificationChannel(applicationContext)
        createUploadPostNotificationChannel(applicationContext)


        val workRequest = PeriodicWorkRequestBuilder<GreetingtWorkManager>(
            repeatInterval = 3,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        ).build()
        val workManager = WorkManager.getInstance(applicationContext)
        workManager.enqueue(workRequest)
    }
}
private fun createGreetingsNotificationChannel(context: Context) {
    val channel = NotificationChannel(
        /* id = */ greetingsChannel,
        /* name = */ "University Greetings",
        /* importance = */ NotificationManager.IMPORTANCE_DEFAULT
    )
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}


private fun createUploadPostNotificationChannel(context: Context) {
    val channelId = uploadPostChannel
    val channelName = "Upload Notifications"
    val channelDescription = "Notifications for image upload progress"
    val importance = NotificationManager.IMPORTANCE_LOW
    val channel = NotificationChannel(channelId, channelName, importance).apply {
        description = channelDescription
    }
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}
