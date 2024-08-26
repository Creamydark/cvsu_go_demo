package com.creamydark.cvsugo.community.feed.presentation.postdetail.presentation.intent

sealed class FeedPostDetailScreenIntent {
    object Delete: FeedPostDetailScreenIntent()
    object OnRefresh: FeedPostDetailScreenIntent()

}