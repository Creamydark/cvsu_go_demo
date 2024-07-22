package com.creamydark.cvsugo.domain.repository

import com.creamydark.cvsugo.domain.enums.AuthenticationState
import kotlinx.coroutines.flow.Flow

interface  UserLoginDataStoreRepo  {

    suspend fun updateLoginState(state:Boolean):Flow<String>

    suspend fun getLoginState():Flow<AuthenticationState>

}