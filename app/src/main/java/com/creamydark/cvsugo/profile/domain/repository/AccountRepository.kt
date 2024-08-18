package com.creamydark.cvsugo.profile.domain.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun getFirebaseUser(): Flow<FirebaseUser?>
}