package com.creamydark.cvsugo.googleAuth.domain.repository

import android.net.Uri
import com.creamydark.cvsugo.googleAuth.domain.dataclass.UserData
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun getFirebaseUser(): Flow<FirebaseUser?>
    suspend fun getUserData(uid:String): Flow<Result<UserData?>>
    suspend fun updateUserData(userData: UserData): Flow<Result<String>>
    suspend fun createAccount(userData: UserData,selectedProfilePicture: Uri?): Flow<Result<String>>
    suspend fun updateProfilePicture(uri: Uri,userData: UserData):Flow<Result<String>>
    suspend fun signOut(): Flow<Result<String>>
}