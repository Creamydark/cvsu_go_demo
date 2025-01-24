package com.creamydark.cvsugo.googleAuth.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.creamydark.cvsugo.core.util.USERS
import com.creamydark.cvsugo.googleAuth.domain.dataclass.UserData
import com.creamydark.cvsugo.googleAuth.domain.repository.AccountRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import javax.inject.Inject


class AccountRepositoryImpl @Inject constructor(
    private val context: Context
): AccountRepository {
    private val auth = Firebase.auth

    private val firestore = Firebase.firestore

    private val storage = Firebase.storage


    override suspend fun getFirebaseUser(): Flow<FirebaseUser?> {
        return callbackFlow {
            val job = launch {
               /* val z = auth.currentUser
               z?.let {
                    for (profile in it.providerData) {
                        // Id of the provider (ex: google.com)
                        val providerId = profile.providerId

                        // UID specific to the provider
                        val uid = profile.uid

                        // Name, email address, and profile photo Url
                        val name = profile.displayName
                        val email = profile.email
                        val photoUrl = profile.photoUrl
                        Log.d("accountRepository", "getFirebaseUser: ${email} | ${name}")
                    }
                }*/
                val user = auth.currentUser
                user?.let {
                    // Name, email address, and profile photo URL
                    val name = it.displayName
                    val email = it.email
                    val photoUrl = it.photoUrl
                    val emailVerified = it.isEmailVerified
                    val uid = it.uid

                    // Log the details
                    Log.d("FirebaseUser", "Name: $name")
                    Log.d("FirebaseUser", "Email: $email")
                    Log.d("FirebaseUser", "Photo URL: $photoUrl")
                    Log.d("FirebaseUser", "Email Verified: $emailVerified")
                    Log.d("FirebaseUser", "UID: $uid")
                }

                auth.addAuthStateListener {
                    user ->
                    trySend(user.currentUser)
                }
            }
            awaitClose {
                job.cancel()
            }
        }
    }

    override suspend fun getUserData(uid: String): Flow<Result<UserData?>> {
        return callbackFlow {
            val job = launch {
                try {
                    firestore.collection(USERS).document(uid).addSnapshotListener { value, error ->
                        val data = value?.toObject(UserData::class.java)
                        trySend(Result.success(data))
                    }
                }catch (e:Exception){
                    trySend(Result.failure(e))
                }
            }
            awaitClose { job.cancel() }
        }
    }

    override suspend fun updateUserData(userData: UserData): Flow<Result<String>> {
        return callbackFlow {
            val job = launch {
                try {
                    if (userData.uid == null) throw Exception("UID is null")
                    firestore.collection(USERS).document(userData.uid).set(userData).await()
                    trySend(Result.success("Success"))
                    close()
                }catch (e:Exception){
                    trySend(Result.failure(e))
                }
            }
            awaitClose { job.cancel() }
        }
    }

    override suspend fun createAccount(
        userData: UserData,
        selectedProfilePicture: Uri?
    ): Flow<Result<String>> = callbackFlow {
        val job = launch {
            try {
                // Validate UID
                val uid = userData.uid ?: throw IllegalArgumentException("UID is null")

                // Check if the username already exists
                val usernameExists = firestore.collection(USERS)
                    .whereEqualTo("username", userData.username)
                    .get()
                    .await()

                if (!usernameExists.isEmpty) {
                    throw IllegalStateException("Username already exists")
                }

                // Upload profile picture if one is selected
                val downloadUrl = selectedProfilePicture?.let { picture ->
                    val storageRef = Firebase.storage.reference.child("users/${userData.uid}/profile_picture")
                    storageRef.putBytes(compressImage(picture,context))
                        .await()
                        .storage
                        .downloadUrl
                        .await()
                }

                // Save user data to Firestore, including profile picture URL if available
                val updatedUserData = userData.copy(profilePictureUri = downloadUrl?.toString())
                firestore.collection(USERS).document(uid).set(updatedUserData).await()

                // Notify success
                trySend(Result.success("Account created successfully"))

            } catch (e: Exception) {
                // Notify failure
                trySend(Result.failure(e))
            } finally {
                // Ensure channel is closed
                close()
            }
        }


        awaitClose { job.cancel() }
    }



    override suspend fun updateProfilePicture(uri: Uri, userData: UserData): Flow<Result<String>> {
        return callbackFlow {

            val job = launch {
                try {
                    // Upload the profile picture
                    val uploadTask = storage
                        .reference
                        .child("users/${userData.uid}/profile_picture").putBytes(
                            compressImage(uri,context)
                        ).await()

                    if (uploadTask.task.isSuccessful) {
                        // Get the download URL
                        val downloadUrl = uploadTask.storage.downloadUrl.await().toString()

                        // Update Firestore with the new profile picture URL
                        val userDocuments = firestore.collection(USERS).whereEqualTo("uid", userData.uid).get().await()

                        for (document in userDocuments.documents) {
                            document.reference.update("profilePictureUri", downloadUrl).await()
                        }

                        // Send success result
                        trySend(Result.success("Update Successfully"))
                    } else {
                        // Handle the error
                        throw uploadTask.task.exception ?: Exception("Unknown error occurred during the upload.")
                    }
                } catch (e: Exception) {
                    // Handle exceptions properly
                    trySend(Result.failure(e))
                } finally {
                    close()
                }
            }

            awaitClose { job.cancel() }
        }
    }

    override suspend fun signOut(): Flow<Result<String>> {
        return callbackFlow {
            val job = launch {
                try {
                    auth.signOut()
                    trySend(Result.success("Signed out successfully"))
                }catch (e:Exception){
                    trySend(Result.failure(e))
                }finally {
                    close()
                }
            }
                awaitClose { job.cancel() }
            }
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