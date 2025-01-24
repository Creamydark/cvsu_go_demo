package com.creamydark.cvsugo.community.profile.presentation.profile.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.googleAuth.domain.dataclass.UserData
import com.creamydark.cvsugo.googleAuth.domain.repository.AccountRepository
import com.creamydark.cvsugo.community.profile.presentation.profile.state.ProfileScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountRepository: AccountRepository
):ViewModel(){
    var state by mutableStateOf(ProfileScreenState())
        private set

    init {
        viewModelScope.launch {
            accountRepository.getFirebaseUser().collectLatest {
                user->
                state = state.copy(user = user)
                accountRepository.getUserData(user?.uid ?: "").collectLatest {

                    value: Result<UserData?> ->
                    value.onSuccess {
                        userData->
                        state = state.copy(userData = userData?: UserData())
                    }
                    value.onFailure {
                        state = state.copy(error = it.message)
                    }
                }
            }
        }
    }
}