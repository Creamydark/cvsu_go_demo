package com.creamydark.cvsugo.auth.presentation.signin.intent

sealed class SignInScreenIntent{
    data class UsernameChanged(val username: String): SignInScreenIntent()
    data class PasswordChanged(val password: String): SignInScreenIntent()
    data object Submit: SignInScreenIntent()

}