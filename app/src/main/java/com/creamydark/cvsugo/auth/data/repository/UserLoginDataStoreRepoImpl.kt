package com.creamydark.cvsugo.auth.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.creamydark.cvsugo.auth.domain.repository.UserLoginDataStoreRepo
import com.creamydark.cvsugo.auth.util.SignInResult
import com.creamydark.cvsugo.core.domain.enums.AuthenticationState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

// Define a Context extension property for DataStore
private val Context.dataStore by preferencesDataStore(name = "settings_preferences")

val userLoginStateKey = booleanPreferencesKey("is_user_logged_in")

class UserLoginDataStoreRepoImpl @Inject constructor(context: Context): UserLoginDataStoreRepo {
    // Create a DataStore instance
    private val dataStore = context.dataStore

    override suspend fun updateLoginState(state: Boolean): Flow<SignInResult> {
        return callbackFlow {
            val job =launch {
                try {
                    dataStore.edit { preferences ->
                        preferences[userLoginStateKey] = state
                    }
                    trySend(SignInResult.Success("Login state updated successfully"))
                    close()
                }catch (e:Exception){
                    trySend(SignInResult.Error(e))
                }
            }
//            trySend("Login state updated successfully")
            awaitClose { job.cancel() }
        }
    }

    override suspend fun getLoginState(): Flow<AuthenticationState> {
        return callbackFlow {
            dataStore.data.map {
                    preferences ->
                preferences[userLoginStateKey] ?: false
            }.collectLatest {
                    value: Boolean ->
                if (value){
                    trySend(AuthenticationState.Authenticated)
                }else{
                    trySend(AuthenticationState.Unauthenticated)
                }
            }
            awaitClose {

            }
        }
    }
}