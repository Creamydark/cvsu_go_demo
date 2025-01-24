package com.creamydark.cvsugo.googleAuth.presentation.signup.intent

sealed class SignUpScreenIntent {
    data class OnEmailChanged(val email: String) : SignUpScreenIntent()
    data class OnPasswordChanged(val password: String) : SignUpScreenIntent()
    data class OnConfirmPasswordChanged(val password: String) : SignUpScreenIntent()
    data object Submit : SignUpScreenIntent()
    object SignIn: SignUpScreenIntent()
    object OneTapSignIn : SignUpScreenIntent()
}