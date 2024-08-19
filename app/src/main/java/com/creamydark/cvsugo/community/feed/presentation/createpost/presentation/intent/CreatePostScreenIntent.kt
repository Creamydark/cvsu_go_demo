package com.creamydark.cvsugo.community.feed.presentation.createpost.presentation.intent

sealed class CreatePostScreenIntent {
    data class OnContentChanged(val content: String) : CreatePostScreenIntent()
    data object OnPublishClicked : CreatePostScreenIntent()


}