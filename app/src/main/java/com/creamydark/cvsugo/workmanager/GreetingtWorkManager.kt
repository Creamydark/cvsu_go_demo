package com.creamydark.cvsugo.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.creamydark.cvsugo.notificationmanager.GreetingsNotificationManager
import kotlinx.coroutines.delay
import java.time.LocalTime

class GreetingtWorkManager(
    appContext: Context,
    params: WorkerParameters
):CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val greetingsNotificationManager = GreetingsNotificationManager(applicationContext)

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

        greetingsNotificationManager.greet(
            title = "Greetings from University",
            text = greetingText
        )
        return Result.success()
    }



}