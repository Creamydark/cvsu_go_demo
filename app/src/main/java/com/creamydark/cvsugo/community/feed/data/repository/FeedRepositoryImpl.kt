package com.creamydark.cvsugo.community.feed.data.repository

import com.creamydark.cvsugo.community.feed.domain.data.PostData
import com.creamydark.cvsugo.community.feed.domain.repository.FeedRepository
import com.creamydark.cvsugo.core.util.POSTS
import com.creamydark.cvsugo.core.util.USERS
import com.creamydark.cvsugo.googleAuth.domain.dataclass.UserData
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(

): FeedRepository {
    private val firestore = Firebase.firestore
    override suspend fun createPost(data: PostData): Flow<Result<String>> {
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
    }

    override suspend fun deletePost(data: PostData): Flow<Result<String>> {
        return callbackFlow {
            val job = launch {
                try {
                    firestore.collection(POSTS).document(data.postId).delete().await()
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
                    val data = firestore.collection(POSTS).document(postId).get().await()
                    trySend(Result.success(data.toObject(PostData::class.java) ?: PostData()))
                }catch (e:Exception){
                    trySend(Result.failure(e))
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
}

