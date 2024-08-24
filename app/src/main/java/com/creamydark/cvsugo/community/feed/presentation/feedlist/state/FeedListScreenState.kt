package com.creamydark.cvsugo.community.feed.presentation.feedlist.state

import com.creamydark.cvsugo.community.feed.domain.data.PostData
import com.creamydark.cvsugo.googleAuth.domain.dataclass.UserData
import com.google.firebase.auth.FirebaseUser

data class FeedListScreenState(
    val currentUser: UserData? = null,
    val loading: Boolean = false,
    val feedList: List<PostData> = emptyList(),
    val error: String? = null
)