package com.creamydark.cvsugo.community.profile.presentation.changeprofilepicture.presentation.state

import android.net.Uri
import com.creamydark.cvsugo.googleAuth.domain.dataclass.UserData
import com.google.firebase.auth.FirebaseUser

data class ChangeProfilePictureScreenState(
    val selectedImage :Uri? = null,
    val userData: UserData = UserData(),
    val firebaseUser: FirebaseUser? = null,
    val loading : Boolean = false,
    val uploading : Boolean = false
)
