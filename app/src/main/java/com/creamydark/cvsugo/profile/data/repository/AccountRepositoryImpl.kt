package com.creamydark.cvsugo.profile.data.repository

import com.creamydark.cvsugo.profile.domain.repository.AccountRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class AccountRepositoryImpl @Inject constructor(
): AccountRepository {
    val auth = FirebaseAuth.getInstance()
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
}