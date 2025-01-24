package com.creamydark.cvsugo.googleAuth.presentation.signup.state

import com.creamydark.cvsugo.googleAuth.components.state.EmailTextFieldState
import com.creamydark.cvsugo.googleAuth.components.state.PasswordTextFieldState

data class SignUpScreenState(
    val email: EmailTextFieldState = EmailTextFieldState(),
    val password: PasswordTextFieldState = PasswordTextFieldState(),
    val confirmPassword: PasswordTextFieldState = PasswordTextFieldState(),
    val loading: Boolean = false,
    val validToProceed:Boolean = email.errorMessage == null && password.errorMessage == null && confirmPassword.errorMessage == null && password.text == confirmPassword.text
)
