package com.creamydark.cvsugo.auth.domain.repository

import com.creamydark.cvsugo.auth.util.SignInResult
import com.creamydark.cvsugo.core.domain.enums.AuthenticationState
import kotlinx.coroutines.flow.Flow

interface  UserLoginDataStoreRepo  {

    suspend fun updateLoginState(state:Boolean):Flow<SignInResult>

    suspend fun getLoginState():Flow<AuthenticationState>

}