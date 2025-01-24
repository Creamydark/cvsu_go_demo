package com.creamydark.cvsugo.accountSetup.presentation.setupnewaccount.intent

import android.net.Uri

sealed class AccountSetupScreenIntent {
    data class OnUsernameChanged(val username: String) : AccountSetupScreenIntent()
    data class OnProfilePictureChanged(val uri: Uri?) : AccountSetupScreenIntent()
    object OnSaveClicked : AccountSetupScreenIntent()
}