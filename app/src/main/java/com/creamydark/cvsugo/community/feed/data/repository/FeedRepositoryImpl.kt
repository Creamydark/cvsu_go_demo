package com.creamydark.cvsugo.community.feed.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.creamydark.cvsugo.community.feed.data.datasource.FeedListDataSource
import com.creamydark.cvsugo.community.feed.domain.data.PostData
import com.creamydark.cvsugo.community.feed.domain.repository.FeedRepository
import com.creamydark.cvsugo.core.util.POSTS
import com.creamydark.cvsugo.core.util.USERS
import com.creamydark.cvsugo.googleAuth.domain.dataclass.UserData
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.UUID
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val context: Context
): FeedRepository {
    private val firestore = Firebase.firestore
    private val storage = Firebase.storage


    override suspend fun createPost(data: PostData, images: List<Uri>): Flow<Result<String>> = callbackFlow {
        val job = launch {
            try {
                // Upload images to storage
                val parsedShits = images.map {
                    Uri.parse(it.toString())
                }
                val imageUrls = images.mapNotNull { uri ->
                    try {
                        val storageRef = storage.reference.child("posts/${UUID.randomUUID()}")
                        val uploadTask = storageRef.putBytes(compressImage(uri,context)).await()
                        storageRef.downloadUrl.await().toString()
                    } catch (e: Exception) {
                        null // Return null if the upload fails for an image
                    }
                }

                if (imageUrls.size != images.size) {
                    // If not all images were uploaded successfully
                    trySend(Result.failure(Exception("Failed to upload all images")))
                    return@launch
                }

                // Update PostData with image URLs
                val updatedData = data.copy(attachments = imageUrls)

                // Create post in Firestore
                firestore.collection(POSTS).add(updatedData).await()
                trySend(Result.success("Success"))
            } catch (e: Exception) {
                trySend(Result.failure(e))
            }
        }

        awaitClose { job.cancel() }
    }


    /*override suspend fun createPost(data: PostData, images: List<Uri>): Flow<Result<String>> {
        return callbackFlow {
            val job = launch {
                try {
                    firestore.collection(POSTS).add(data).await()
                    trySend(Result.success("Success"))
                }catch (e:Exception){
                    trySend(Result.failure(e))
                }
            }
            awaitClose {
                job.cancel()
            }
        }
    }*/

    override suspend fun deletePost(data: PostData): Flow<Result<String>> {
        return callbackFlow {
            val job = launch {
                try {
                    // Fetch the post to get image URLs
                    val postSnapshot = firestore.collection(POSTS).document(data.postId).get().await()
                    val postData = postSnapshot.toObject(PostData::class.java)

                    if (postData == null) {
                        trySend(Result.failure(Exception("Post not found")))
                        return@launch
                    }

                    // Delete each image from  Storage
                    postData.attachments.forEach { imageUrl ->
                        try {
                            val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl)
                            storageRef.delete().await()
                        } catch (e: Exception) {
                            trySend(Result.failure(e))
                            return@launch
                        }
                    }

                    // Delete the post from Firestore
                    firestore.collection(POSTS).document(data.postId).delete().await()
                    trySend(Result.success("Post deleted successfully"))
                } catch (e: Exception) {
                    trySend(Result.failure(e))
                }
            }
            awaitClose { job.cancel() }
        }
    }

    override suspend fun getPosts(): Flow<Result<List<PostData>>> {
        return callbackFlow {
            val job = launch {
                firestore.collection(POSTS).addSnapshotListener { value, error ->
                    if (error != null) {
                        trySend(Result.failure(error))
                        return@addSnapshotListener
                    }
                    val posts = value?.toObjects<PostData>() ?: emptyList()

                    // Create a list of tasks to fetch user data
                    val tasks = posts.map { post ->
                        firestore.collection(USERS).document(post.userId).get().continueWith { task ->
                            val userData = task.result.toObject(UserData::class.java) ?: UserData()
                            post.copy(userData = userData)
                        }
                    }

                    // Wait for all tasks to complete
                    Tasks.whenAllComplete(tasks).addOnSuccessListener {
                        val newPosts = tasks.mapNotNull { it.result }
                        trySend(Result.success(newPosts.sortedByDescending { it.theTimestamp }))
                    }.addOnFailureListener { e ->
                        trySend(Result.failure(e))
                    }
                }

            }
            awaitClose {
                job.cancel()
            }
        }
    }

    override suspend fun getSpecificPost(postId: String): Flow<Result<PostData>> {
        return callbackFlow {
            val job = launch {
                try {
                    val postData = firestore.collection(POSTS).document(postId).get().await().toObject(PostData::class.java)?: PostData()
                    val task = firestore.collection(USERS).document(postData.userId).get().continueWith { task ->
                        val userData = task.result.toObject(UserData::class.java) ?: UserData()
                        postData.copy(userData = userData)
                    }
                    val updatedPostData = task.await()
                    trySend(Result.success(updatedPostData))
                }catch (e:Exception){
                    trySend(Result.failure(e))
                }finally {
                    close()
                }
            }
            awaitClose {
                job.cancel()
            }
        }
    }

    override suspend fun updatePost(data: PostData): Flow<Result<String>> {
        return callbackFlow {
            val job = launch {
                try {
                    firestore.collection(POSTS).document(data.postId).set(data).await()
                    trySend(Result.success("Success"))
                }catch (e:Exception){
                    trySend(Result.failure(e))
                }
            }
            awaitClose {
                job.cancel()
            }
        }
    }

    override suspend fun getPostsUsingPager(): Flow<PagingData<PostData>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { FeedListDataSource() }
        ).flow

    }
}


private fun compressImage(imageUri: Uri,context: Context): ByteArray {
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
