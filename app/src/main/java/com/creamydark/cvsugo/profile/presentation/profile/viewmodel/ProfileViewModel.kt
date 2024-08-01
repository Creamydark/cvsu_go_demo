package com.creamydark.cvsugo.profile.presentation.profile.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.auth.domain.repository.UserLoginDataStoreRepo
import com.creamydark.cvsugo.profile.presentation.profile.state.ProfileScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepo: UserLoginDataStoreRepo
):ViewModel(){
    var state by mutableStateOf(ProfileScreenState())
        private set

    init {
        viewModelScope.launch {
            authRepo.getLoginState().collectLatest {
                state = state.copy(
                    authState = it
                )
            }
        }
    }
}