package com.creamydark.cvsugo.googleAuth.presentation.signup.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.auth.util.SignInResult
import com.creamydark.cvsugo.core.domain.validator.EmailValidator
import com.creamydark.cvsugo.core.domain.validator.PasswordValidator
import com.creamydark.cvsugo.googleAuth.domain.repository.SignInRepository
import com.creamydark.cvsugo.googleAuth.presentation.signup.intent.SignUpScreenIntent
import com.creamydark.cvsugo.googleAuth.presentation.signup.state.SignUpScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: SignInRepository
):ViewModel() {
    var state by mutableStateOf(SignUpScreenState())
        private set

    private val channel = Channel<SignInResult>()
    val receiver = channel.receiveAsFlow()

    fun onIntent(intent: SignUpScreenIntent) {
        when (intent) {
            is SignUpScreenIntent.OnEmailChanged -> {
                state = state.copy(email = state.email.copy(text = intent.email))
                validateEmail()
            }

            is SignUpScreenIntent.OnPasswordChanged -> {
                state = state.copy(password = state.password.copy(text = intent.password))
                validatePassword()
            }
            is SignUpScreenIntent.OnConfirmPasswordChanged -> {
                state = state.copy(confirmPassword = state.confirmPassword.copy(text = intent.password))
                passwordMatch()
            }
            is SignUpScreenIntent.Submit -> {
                if (state.validToProceed){
                    state = state.copy(loading = true)
                    viewModelScope.launch {
                        repository.signUp(state.email.text, state.password.text).collectLatest{
                                value: SignInResult ->
                            channel.trySend(value)
                            state = state.copy(loading = false)
                        }
                    }
                }
            }
            else -> {}
        }
    }


    private fun passwordMatch(){
        val valid = state.password.text == state.confirmPassword.text
        state = state.copy(confirmPassword = state.confirmPassword.copy(errorMessage = if (valid) null else "Passwords do not match"))
    }
    private fun validateEmail(){
        val valid = EmailValidator().isValidEmail(state.email.text)
        state = state.copy(email = state.email.copy(errorMessage = if (valid) null else "Invalid email"))
    }

    private fun validatePassword(){
        val valid = PasswordValidator().validate(state.password.text)
        state = state.copy(password = state.password.copy(errorMessage = if (valid) null else "too short"))
    }


}