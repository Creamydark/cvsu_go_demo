package com.creamydark.cvsugo.core.presentation.rootscreen.intent

sealed class MainScreenIntent {
    object LoadUser : MainScreenIntent()
    object LoadLoginState : MainScreenIntent()
    object SignOut : MainScreenIntent()
    data class SetLoginState(val state: Boolean) : MainScreenIntent()
}
