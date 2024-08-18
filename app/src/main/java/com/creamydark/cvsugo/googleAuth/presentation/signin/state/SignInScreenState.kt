package com.creamydark.cvsugo.googleAuth.presentation.signin.state

import com.creamydark.cvsugo.googleAuth.components.state.EmailTextFieldState
import com.creamydark.cvsugo.googleAuth.components.state.PasswordTextFieldState

data class SignInScreenState(
    val email: EmailTextFieldState = EmailTextFieldState(),
    val password: PasswordTextFieldState = PasswordTextFieldState(),
)
