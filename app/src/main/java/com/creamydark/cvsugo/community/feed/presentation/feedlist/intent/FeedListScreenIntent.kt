package com.creamydark.cvsugo.community.feed.presentation.feedlist.intent

import com.creamydark.cvsugo.community.feed.domain.data.PostData

sealed class FeedListScreenIntent {
    data class OnDeletePost(val postData: PostData):FeedListScreenIntent()
    data class OnEditPost(val postData: PostData):FeedListScreenIntent()
    data class NavigateToDetail(val postId:String):FeedListScreenIntent()
}