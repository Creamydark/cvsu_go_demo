package com.creamydark.cvsugo.core.presentation.state

import com.creamydark.cvsugo.core.domain.enums.AuthenticationState
import com.creamydark.cvsugo.core.domain.enums.AuthenticationState.Loading
import com.creamydark.cvsugo.googleAuth.domain.dataclass.UserData
import com.google.firebase.auth.FirebaseUser

data class MainScreenState(
    val firebaseUser: FirebaseUser? = null,
    val currentUser: UserData? = null,
    val authenticationState: AuthenticationState = Loading,
    val portalAuthState: AuthenticationState = Loading,
)
