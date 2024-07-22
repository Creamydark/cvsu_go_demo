package com.creamydark.cvsugo

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp():Application() {
    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            /* id = */ "channel_id_0",
            /* name = */ "University Greetings",
            /* importance = */ NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}