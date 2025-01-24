package com.creamydark.cvsugo.community.feed.presentation.createpost.presentation.intent

import android.net.Uri

sealed class CreatePostScreenIntent {
    data class OnContentChanged(val content: String) : CreatePostScreenIntent()
    data object OnPublishClicked : CreatePostScreenIntent()
    data class OnGetImages(val images: List<Uri>) : CreatePostScreenIntent()
    data class OnRemoveImage(val index: Int) : CreatePostScreenIntent()

}