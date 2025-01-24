import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.creamydark.cvsugo.R
import com.creamydark.cvsugo.core.notificationmanager.NotificationCHIds.ANNOUNCEMENTS_CHANNEL
import com.creamydark.cvsugo.core.util.ANNOUNCEMENTS
import com.creamydark.cvsugo.notification.domain.data.NotificationData
import com.creamydark.cvsugo.notification.domain.repository.NotificationRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class AnnouncementsWorker @AssistedInject constructor(
    @Assisted appContext: Context,          // The Context is injected here
    @Assisted params: WorkerParameters,     // Worker parameters
//    val notificationRepository: NotificationRepository
) : CoroutineWorker(appContext, params) {
    val firestore = Firebase.firestore



    override suspend fun doWork(): Result {
        Log.d("AnnouncementsWorker", "doWork:${Thread.currentThread().name} ")

        initNotification()
        // Fetch and display announcements here
//        val notificationRepository = NotificationRepositoryImpl()
        /*notificationRepository.getAnouncements().collect {
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
                e->
                return@onFailure
            }
        }*/
        return Result.success()
    }

    private fun initNotification(){
        firestore.collection(ANNOUNCEMENTS).addSnapshotListener { value, error ->
            val data = value?.toObjects(NotificationData::class.java)?: emptyList()
            data.forEachIndexed { index, notificationData ->
                notifyUser(
                    context = applicationContext,
                    notificationId = index,
                    title = notificationData.title,
                    body = notificationData.message
                )
            }
        }
    }

    private fun notifyUser(
        context: Context,
        notificationId: Int = 0,
        title: String = "",
        body: String = ""
    ) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(context, ANNOUNCEMENTS_CHANNEL)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.outline_notifications_24)
            .setPriority(NotificationCompat.PRIORITY_LOW)

        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}
