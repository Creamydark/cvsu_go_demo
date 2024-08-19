package com.creamydark.cvsugo.community.feed.presentation.createpost.presentation.state

import com.creamydark.cvsugo.community.feed.domain.PostVisibilityOptions

data class CreatePostScreenState(
    val content: String = "",
    val visibility: PostVisibilityOptions = PostVisibilityOptions.PUBLIC,
    val attachments: List<String> = emptyList(),
    val loadingPublishBTN: Boolean = false
)
