package com.creamydark.cvsugo.accountsignin.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.creamydark.cvsugo.core.domain.enums.AuthenticationState
import com.creamydark.cvsugo.accountsignin.domain.repository.UserLoginDataStoreRepo
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// Define a Context extension property for DataStore
private val Context.dataStore by preferencesDataStore(name = "settings_preferences")

val userLoginStateKey = booleanPreferencesKey("is_user_logged_in")

class UserLoginDataStoreRepoImpl @Inject constructor(context: Context): UserLoginDataStoreRepo {
    // Create a DataStore instance
    private val dataStore = context.dataStore

    override suspend fun updateLoginState(state: Boolean): Flow<String> {
        return callbackFlow {
            dataStore.edit { preferences ->
                preferences[userLoginStateKey] = state
            }
            trySend("Login state updated successfully")
            close()
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