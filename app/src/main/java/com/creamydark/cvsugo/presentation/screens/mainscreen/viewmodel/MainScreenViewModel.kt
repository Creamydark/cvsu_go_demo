package com.creamydark.cvsugo.presentation.screens.mainscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.domain.repository.UserLoginDataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val userLoginDataStoreRepo: UserLoginDataStoreRepo
) : ViewModel() {
    fun isUserLoggedIn () = userLoginDataStoreRepo.getLoginState()
    fun setLoginState (state: Boolean) {
        viewModelScope.launch {
            userLoginDataStoreRepo.updateLoginState(state).collect {
                // do nothing
            }
        }
    }
}