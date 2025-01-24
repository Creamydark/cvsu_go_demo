package com.creamydark.cvsugo.googleAuth.domain.dataclass

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class UserData(
    val username: String = "",
    val uid:String? = null,
    val profilePictureUri: String? = null,
    val email: String? = null,
    val name:String? = null,
    @ServerTimestamp val dateJoined:Date = Date()
)
