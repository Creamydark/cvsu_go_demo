package com.creamydark.cvsugo.community.feed.presentation.postdetail.state

import com.creamydark.cvsugo.community.feed.domain.data.PostData

data class FeedPostDetailScreenState(
    val loading: Boolean = false,
    val post: PostData? = null,
)
