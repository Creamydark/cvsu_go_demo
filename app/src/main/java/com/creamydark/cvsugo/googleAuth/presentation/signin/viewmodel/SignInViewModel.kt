package com.creamydark.cvsugo.googleAuth.presentation.signin.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.auth.util.SignInResult
import com.creamydark.cvsugo.core.domain.validator.EmailValidator
import com.creamydark.cvsugo.googleAuth.domain.repository.SignInRepository
import com.creamydark.cvsugo.googleAuth.presentation.signin.intent.SignInScreenIntent
import com.creamydark.cvsugo.googleAuth.presentation.signin.state.SignInScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInRepository: SignInRepository
): ViewModel() {
    var state by mutableStateOf(SignInScreenState())
        private  set

    private val signInResult = Channel<SignInResult>()
    val signInResultReceiver = signInResult.receiveAsFlow()

    fun onIntent(intent: SignInScreenIntent){
        when (intent) {
            is SignInScreenIntent.OnEmailChanged -> {
                state = state.copy(email = state.email.copy(text = intent.email))
                validateEmail()
            }
            is SignInScreenIntent.OnPasswordChanged -> {
                state = state.copy(password = state.password.copy(text = intent.password))
            }
            is SignInScreenIntent.Submit -> {
                viewModelScope.launch {
                    signInRepository.signIn(state.email.text, state.password.text).collectLatest {
                        value: SignInResult ->
                        signInResult.trySend(value)
                    }
                }
            }
            else -> {}
        }
    }

    private fun validateEmail(){
        val valid = EmailValidator().isValidEmail(state.email.text)
        state = state.copy(email = state.email.copy(errorMessage = if (valid) null else "Invalid email"))
    }
}