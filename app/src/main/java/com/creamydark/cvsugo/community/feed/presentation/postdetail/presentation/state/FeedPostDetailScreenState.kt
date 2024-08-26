package com.creamydark.cvsugo.community.feed.presentation.postdetail.presentation.state

import com.creamydark.cvsugo.community.feed.domain.data.PostData

data class FeedPostDetailScreenState(
    val loading: Boolean = false,
    val post: PostData? = null,
    val showEditButton: Boolean = false,
)
