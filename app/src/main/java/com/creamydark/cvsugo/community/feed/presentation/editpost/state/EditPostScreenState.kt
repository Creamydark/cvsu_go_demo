package com.creamydark.cvsugo.community.feed.presentation.editpost.state

import com.creamydark.cvsugo.community.feed.domain.data.PostData

data class EditPostScreenState(
    val loading: Boolean = false,
    val post: PostData? = null,
    val updating : Boolean = false
)

