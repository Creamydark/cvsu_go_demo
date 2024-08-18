package com.creamydark.cvsugo.googleAuth.presentation.oneClickSignIn.intent

sealed class OneClickSignInScreenIntent() {
    data object OnSignInButtonClick : OneClickSignInScreenIntent()
    data class OnTokenIdReceived(val tokenId: String) : OneClickSignInScreenIntent()
    data class OnDialogDismissed(val error: String) : OneClickSignInScreenIntent()

}