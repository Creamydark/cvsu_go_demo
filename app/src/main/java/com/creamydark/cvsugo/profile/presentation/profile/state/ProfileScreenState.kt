package com.creamydark.cvsugo.profile.presentation.profile.state

import com.creamydark.cvsugo.core.domain.enums.AuthenticationState

data class ProfileScreenState(
    val authState: AuthenticationState = AuthenticationState.Loading,
    val isLoading: Boolean = false,
    val error: String? = null
)