package com.creamydark.cvsugo.profile.presentation.profile.state

import com.creamydark.cvsugo.core.domain.enums.AuthenticationState
import com.google.firebase.auth.FirebaseUser

data class ProfileScreenState(
    val authState: AuthenticationState = AuthenticationState.Loading,
    val isLoading: Boolean = false,
    val error: String? = null,
    val user: FirebaseUser? = null
)