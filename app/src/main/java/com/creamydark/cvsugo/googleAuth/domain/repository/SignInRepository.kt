package com.creamydark.cvsugo.googleAuth.domain.repository

import com.creamydark.cvsugo.auth.util.SignInResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface SignInRepository {
    suspend fun signIn(email: String, password: String): Flow<SignInResult>
    suspend fun signUp(email: String, password: String): Flow<SignInResult>
    suspend fun currentUserListener():Flow<FirebaseUser?>
    suspend fun oneTapSignIn(tokenId:String):Flow<SignInResult>
}