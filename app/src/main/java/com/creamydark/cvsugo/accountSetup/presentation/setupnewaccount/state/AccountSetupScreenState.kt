package com.creamydark.cvsugo.accountSetup.presentation.setupnewaccount.state

import android.net.Uri

data class AccountSetupScreenState(
    val username: String = "",
    val profilePictureUri: String? = null,
    val selectedImageUri: Uri? = null,
    val loading: Boolean = false,
    val error: String? = null,
)
