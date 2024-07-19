package com.creamydark.cvsugo.domain.repository

import kotlinx.coroutines.flow.Flow

interface  UserLoginDataStoreRepo  {

    suspend fun updateLoginState(state:Boolean):Flow<String>

    fun getLoginState():Flow<Boolean>

}