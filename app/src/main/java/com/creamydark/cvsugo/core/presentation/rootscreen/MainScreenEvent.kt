package com.creamydark.cvsugo.core.presentation.rootscreen



sealed class MainScreenEvent {
    data class ShowError(val message: String) : MainScreenEvent()
    object NavigateToLogin : MainScreenEvent()
    object NavigateToUniversity : MainScreenEvent()
}
