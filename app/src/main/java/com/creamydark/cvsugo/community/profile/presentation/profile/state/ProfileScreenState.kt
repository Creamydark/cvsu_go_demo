package com.creamydark.cvsugo.community.profile.presentation.profile.state

import com.creamydark.cvsugo.core.domain.enums.AuthenticationState
import com.creamydark.cvsugo.googleAuth.domain.dataclass.UserData
import com.google.firebase.auth.FirebaseUser

data class ProfileScreenState(
    val authState: AuthenticationState = AuthenticationState.Loading,
    val isLoading: Boolean = false,
    val error: String? = null,
    val user: FirebaseUser? = null,
    val userData: UserData = UserData()
)