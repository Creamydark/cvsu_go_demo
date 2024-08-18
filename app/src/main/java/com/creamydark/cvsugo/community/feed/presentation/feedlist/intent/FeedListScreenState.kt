package com.creamydark.cvsugo.community.feed.presentation.feedlist.intent

import com.creamydark.cvsugo.community.feed.domain.data.PostData

data class FeedListScreenState(
    val loading: Boolean = false,
    val feedList: List<PostData> = emptyList(),
    val error: String? = null
)