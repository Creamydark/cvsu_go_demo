package com.creamydark.cvsugo.presentation.screens.signin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.domain.repository.UserLoginDataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userLoginDataStoreRepo: UserLoginDataStoreRepo
): ViewModel() {
//    fun isUserLoggedIn () = userLoginDataStoreRepo.getLoginState()
    fun updateLoginState (state: Boolean){
        viewModelScope.launch {
            userLoginDataStoreRepo.updateLoginState(state).collectLatest {
                m->
            }
        }
    }
}