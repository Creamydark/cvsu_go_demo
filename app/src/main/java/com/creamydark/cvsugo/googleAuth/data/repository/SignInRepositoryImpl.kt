package com.creamydark.cvsugo.googleAuth.data.repository

import com.creamydark.cvsugo.auth.util.SignInResult
import com.creamydark.cvsugo.googleAuth.domain.repository.SignInRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(

): SignInRepository {
    val auth = FirebaseAuth.getInstance()
    override suspend fun signIn(email: String, password: String): Flow<SignInResult> {
        return callbackFlow {
            val job = launch {
                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                    trySend(SignInResult.Success("Success"))
                    close()
                }catch (e:Exception){
                    trySend(SignInResult.Error(e))
                }
            }
            awaitClose { job.cancel() }
        }
    }

    override suspend fun signUp(email: String, password: String): Flow<SignInResult> {
        return callbackFlow {
            val job = launch {
                try {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        task ->
                        if (task.isSuccessful){
                            val user = auth.currentUser
                            user?.sendEmailVerification()?.addOnCompleteListener {
                                sendEmailTask->
                                if (sendEmailTask.isSuccessful){
                                    trySend(SignInResult.Success("Verification email sent to ${user.email}"))
                                    close()
                                }
                            }
                        }
                    }
                }catch (e:Exception){
                    trySend(SignInResult.Error(e))
                }
            }
            awaitClose { job.cancel() }
        }
    }

    override suspend fun currentUserListener(): Flow<FirebaseUser?> {
        return callbackFlow {
            auth.addAuthStateListener {
                currentAuth ->
                trySend(currentAuth.currentUser)
            }
            awaitClose {  }
        }
    }

    override suspend fun oneTapSignIn(tokenId: String): Flow<SignInResult> {
        return callbackFlow {
            val job = launch {
                try {

                    val firebaseCredential = GoogleAuthProvider.getCredential(tokenId,null )
                    auth.signInWithCredential(firebaseCredential).await()
                    trySend(SignInResult.Success("Success"))
                    close()
                }catch (e:Exception){
                    trySend(SignInResult.Error(e))
                }
            }
            awaitClose { job.cancel() }
        }
    }
}