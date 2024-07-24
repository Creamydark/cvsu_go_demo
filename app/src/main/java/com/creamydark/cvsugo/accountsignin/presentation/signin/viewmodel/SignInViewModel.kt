package com.creamydark.cvsugo.accountsignin.presentation.signin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.accountsignin.domain.repository.UserLoginDataStoreRepo
import com.creamydark.cvsugo.core.domain.enums.AuthenticationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userLoginDataStoreRepo: UserLoginDataStoreRepo
): ViewModel() {
//    fun isUserLoggedIn () = userLoginDataStoreRepo.getLoginState()
    private val _authState = MutableStateFlow<AuthenticationState>(AuthenticationState.Loading)
    val authState: StateFlow<AuthenticationState> get() = _authState
    fun updateLoginState (state: Boolean){
        viewModelScope.launch {
            userLoginDataStoreRepo.updateLoginState(state).collectLatest {
                m->
            }
        }
    }
}