package com.creamydark.cvsugo.workmanager

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.creamydark.cvsugo.R
import kotlinx.coroutines.delay
import java.time.LocalTime

class GreetingtWorkManager(
    appContext: Context,
    params: WorkerParameters
):CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {

        delay(3000)

        val currentHour = LocalTime.now().hour
        val greetingText = when (currentHour){
            in 6..11 -> listOf(
                "Rise and shine, cvSUHenyo!",
                "Good morning, cvSUHenyo! Grab your coffee and let's tackle this day together.",
                "Top of the morning to you, cvSUHenyo!"
            ).random()
            in 12..17 -> listOf(
                "Good afternoon, cvSUHenyo! Halfway through, keep that energy up!",
                "Afternoon, cvSUHenyo! Time for a quick study break?",
                "Hey cvSUHenyo, afternoon already! Don't miss out on any important updates."
            ).random()
            in 18..23 -> listOf(
                "Good evening, cvSUHenyo! Time to wind down and reflect on a productive day.",
                "Evening, cvSUHenyo! Catch up on any announcements you might have missed.",
                "Hello cvSUHenyo, hope you had a great day! Plan your tomorrow before you rest."
            ).random()
            else -> listOf(
                "Burning the midnight oil, cvSUHenyo?",
                "Night owl, cvSUHenyo? We're always available, even at this hour.",
                "Hey cvSUHenyo, still up? Don't forget to take breaks!"
            ).random()
        }
        showNotification(
            title = greetingText,
            text = "Greetings from University"
        )
        return Result.success()
    }

    private fun showNotification(title:String="",text:String = ""){
        val notificationManager = getSystemService(applicationContext,NotificationManager::class.java) as NotificationManager
        val notification = NotificationCompat.Builder(
            applicationContext,
            "channel_id_0"
        )
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.baseline_groups_24)
            .build()
        notificationManager.notify(0,notification)
    }

}