package com.creamydark.cvsugo.googleAuth.presentation.signin.intent

sealed class SignInScreenIntent {
    data class OnEmailChanged(val email: String): SignInScreenIntent()
    data class OnPasswordChanged(val password: String): SignInScreenIntent()
    object Submit: SignInScreenIntent()
    object SignUp: SignInScreenIntent()
}