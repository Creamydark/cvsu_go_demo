package com.creamydark.cvsugo.accountsignin.domain.repository

import com.creamydark.cvsugo.core.domain.enums.AuthenticationState
import kotlinx.coroutines.flow.Flow

interface  UserLoginDataStoreRepo  {

    suspend fun updateLoginState(state:Boolean):Flow<String>

    suspend fun getLoginState():Flow<AuthenticationState>

}