package com.creamydark.cvsugo.community.feed.presentation.postdetail.presentation.event

sealed class FeedPostDetailScreenEvent{
    data object DeleteSuccess: FeedPostDetailScreenEvent()
    data object DeleteFailure: FeedPostDetailScreenEvent()
}