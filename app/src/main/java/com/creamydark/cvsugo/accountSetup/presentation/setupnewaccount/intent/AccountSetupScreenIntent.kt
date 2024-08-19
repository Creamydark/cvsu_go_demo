package com.creamydark.cvsugo.accountSetup.presentation.setupnewaccount.intent

sealed class AccountSetupScreenIntent {
    data class OnUsernameChanged(val username: String) : AccountSetupScreenIntent()
    data class OnProfilePictureChanged(val profilePictureUri: String?) : AccountSetupScreenIntent()
    object OnSaveClicked : AccountSetupScreenIntent()
}