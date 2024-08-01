package com.creamydark.cvsugo.auth.presentation.signin.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.auth.domain.repository.UserLoginDataStoreRepo
import com.creamydark.cvsugo.auth.presentation.signin.intent.SignInScreenIntent
import com.creamydark.cvsugo.auth.presentation.signin.state.SignInScreenState
import com.creamydark.cvsugo.auth.util.SignInResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userLoginDataStoreRepo: UserLoginDataStoreRepo
): ViewModel() {
    var state by mutableStateOf(SignInScreenState())
        private set


    private val channel = Channel<SignInResult>()
    val result = channel.receiveAsFlow()

    fun processIntent(intent: SignInScreenIntent){
        when (intent) {
            is SignInScreenIntent.PasswordChanged -> {
                state = state.copy(
                    password = intent.password
                )
            }
            is SignInScreenIntent.UsernameChanged -> {
                state = state.copy(
                    studentID = intent.username
                )
            }
            SignInScreenIntent.Submit -> {
                viewModelScope.launch {
                    userLoginDataStoreRepo.updateLoginState(true).collectLatest {
                        value: SignInResult ->
                        channel.trySend(value)
                    }
                }
            }
        }
    }
}