package com.creamydark.cvsugo.community.profile.presentation.changeprofilepicture.presentation.intent

import android.net.Uri

sealed class ChangeProfilePictureScreenIntent {
    data class SelectImage(val image:Uri?): ChangeProfilePictureScreenIntent()
    data object Update : ChangeProfilePictureScreenIntent()
}