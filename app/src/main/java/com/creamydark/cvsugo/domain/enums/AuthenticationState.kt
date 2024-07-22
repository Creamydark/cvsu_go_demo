package com.creamydark.cvsugo.domain.enums

enum class AuthenticationState {
    Authenticated,
    Unauthenticated,
    ErrorSigningIn,
    Loading,
    OnRegister
}