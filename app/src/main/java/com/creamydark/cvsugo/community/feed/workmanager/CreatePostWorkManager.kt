package com.creamydark.cvsugo.community.feed.workmanager

import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.creamydark.cvsugo.R
import com.creamydark.cvsugo.community.feed.domain.data.PostData
import com.creamydark.cvsugo.core.notificationmanager.NotificationCHIds.uploadPostChannel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.UUID

class CreatePostWorkManager(
    context: Context,
    workerParameters: WorkerParameters
):CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = runBlocking {
        try {

            notifyUser(applicationContext,true)

            val jsonData = inputData.getString("post_data") ?: return@runBlocking Result.failure()
            val imagesData = inputData.getStringArray("image_uris") ?: return@runBlocking Result.failure()


            // Deserialize the data
            val postData = Gson().fromJson(jsonData, PostData::class.java)
            val imageUris = imagesData.map { Uri.parse(it) }


            val firestore = FirebaseFirestore.getInstance()
            val storage = FirebaseStorage.getInstance()

            val storageRef = storage.reference.child("posts/${UUID.randomUUID()}")
            // Upload images


            val imageUrls = imageUris.mapNotNull { uri ->
                try {
                    val imageref = storageRef.child("${UUID.randomUUID()}")
                    val uploadTask = imageref.putBytes(compressImage(uri, applicationContext)).await()
                    imageref.downloadUrl.await().toString()
                } catch (e: Exception) {
                    null // Handle upload failure
                }
            }

            if (imageUrls.size != imageUris.size) {
                return@runBlocking Result.failure(Data.Builder().putString("error", "Failed to upload all images").build())
            }

            // Create post in Firestore with the uploaded image URLs
            val updatedData = postData.copy(attachments = imageUrls)
            firestore.collection("posts").add(updatedData).await()
            notifyUser(applicationContext,false,"Posted successfully",)
            return@runBlocking Result.success()
        } catch (e: Exception) {
            return@runBlocking Result.failure(Data.Builder().putString("error", e.message).build())
        }
    }

}

private fun compressImage(imageUri: Uri, context: Context): ByteArray {
    val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(imageUri))
    // Create a new bitmap with the desired quality
    val compressedBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width / 2, bitmap.height / 2, false)
    // Convert the compressed bitmap to a byte array
    val outputStream = ByteArrayOutputStream()
    val quality = if(bitmap.height>=300||bitmap.width>=300){
        70
    }else{
        100
    }
    compressedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
    return outputStream.toByteArray()
}

private fun notifyUser(context: Context, onGoing:Boolean,title:String = "Uploading images"){
    // Notification ID and Builder\
    val icon = if( onGoing ) R.drawable.outline_cloud_upload_24 else R.drawable.outline_done_24
    val notificationId = 1
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val notificationBuilder = NotificationCompat.Builder(context, uploadPostChannel)
        .setContentTitle(title)
        .setSmallIcon(icon)
        .setOngoing(onGoing)
        .setPriority(NotificationCompat.PRIORITY_LOW)

    // Start Notification
    notificationManager.notify(notificationId, notificationBuilder.build())
}