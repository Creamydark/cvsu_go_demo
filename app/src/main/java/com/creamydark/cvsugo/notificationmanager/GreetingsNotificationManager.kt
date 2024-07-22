package com.creamydark.cvsugo.notificationmanager

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.creamydark.cvsugo.R

class GreetingsNotificationManager(private val context : Context) {
    fun greet(title:String="",text:String = ""){
        val notificationManager = getSystemService(context,NotificationManager::class.java) as NotificationManager
        val notification = NotificationCompat.Builder(
            context,
            "channel_id_0"
        )
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.baseline_groups_24)
            .build()
        notificationManager.notify(0,notification)
    }
}