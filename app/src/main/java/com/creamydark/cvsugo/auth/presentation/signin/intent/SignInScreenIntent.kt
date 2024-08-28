package com.creamydark.cvsugo.auth.presentation.signin.intent

import com.creamydark.cvsugo.core.domain.enums.UserChooserType

sealed class SignInScreenIntent{
    data class UsernameChanged(val username: String): SignInScreenIntent()
    data class PasswordChanged(val password: String): SignInScreenIntent()
    data class Submit(val userChooserType: UserChooserType): SignInScreenIntent()
}