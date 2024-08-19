package com.creamydark.cvsugo.accountSetup.presentation.setupnewaccount.state

data class AccountSetupScreenState(
    val username: String = "",
    val profilePictureUri: String? = null,
    val loading: Boolean = false,
    val error: String? = null,
)
