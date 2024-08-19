package com.creamydark.cvsugo.googleAuth.data.repository

import com.creamydark.cvsugo.core.util.USERS
import com.creamydark.cvsugo.googleAuth.domain.dataclass.UserData
import com.creamydark.cvsugo.googleAuth.domain.repository.AccountRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AccountRepositoryImpl @Inject constructor(
): AccountRepository {
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()



    override suspend fun getFirebaseUser(): Flow<FirebaseUser?> {
        return callbackFlow {
            val job = launch {
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

    override suspend fun createAccount(userData: UserData): Flow<Result<String>> {
        return callbackFlow {
            val job = launch {
                try {
                    if (userData.uid == null) throw Exception("UID is null")
                    firestore.collection(USERS).whereEqualTo("username", userData.username).get().addOnCompleteListener {
                        checkExistTask ->
                        if (checkExistTask.result.isEmpty){
                            firestore.collection(USERS).document(userData.uid).set(userData).addOnCompleteListener {
                                createTask ->
                                if (createTask.isSuccessful){
                                    trySend(Result.success("Success"))
                                    close()
                                }else{
                                    throw createTask.exception ?: Exception("Unknown error")
                                }
                            }
                        }else{
                            throw Exception("Username already exists")
                        }
                    }
                }catch (e:Exception){
                    trySend(Result.failure(e))
                }
            }
            awaitClose { job.cancel() }
        }
    }
}