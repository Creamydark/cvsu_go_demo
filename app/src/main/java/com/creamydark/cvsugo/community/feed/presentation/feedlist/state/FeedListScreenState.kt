package com.creamydark.cvsugo.community.feed.presentation.feedlist.state

import androidx.paging.PagingData
import com.creamydark.cvsugo.community.feed.domain.data.PostData
import com.creamydark.cvsugo.googleAuth.domain.dataclass.UserData

data class FeedListScreenState(
    val currentUser: UserData? = null,
    val loading: Boolean = false,
    val feedList: List<PostData> = emptyList(),
    val isRefreshing: Boolean = false,
    val error: String? = null,
)