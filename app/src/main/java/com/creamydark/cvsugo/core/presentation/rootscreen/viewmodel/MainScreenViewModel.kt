package com.creamydark.cvsugo.core.presentation.rootscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.core.domain.enums.AuthenticationState
import com.creamydark.cvsugo.auth.domain.repository.UserLoginDataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val userLoginDataStoreRepo: UserLoginDataStoreRepo
) : ViewModel() {

    private val _authenticationState = MutableStateFlow(AuthenticationState.Loading)
    val authenticationState: StateFlow<AuthenticationState> get() = _authenticationState
    init {
        viewModelScope.launch {
            userLoginDataStoreRepo.getLoginState().collectLatest {
                value: AuthenticationState ->
//                delay(2000)
                _authenticationState.update { value }
            }
        }
    }
    fun setLoginState (state: Boolean) {
        viewModelScope.launch {
            userLoginDataStoreRepo.updateLoginState(state).collect {
                // do nothing
            }
        }
    }
}