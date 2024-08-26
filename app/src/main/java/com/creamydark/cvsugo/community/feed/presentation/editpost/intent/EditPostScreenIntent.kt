package com.creamydark.cvsugo.community.feed.presentation.editpost.intent

sealed class EditPostScreenIntent {
    data class  OnContentChanged(val content: String) : EditPostScreenIntent()
    data object OnPublishClicked : EditPostScreenIntent()
    object OnRefresh : EditPostScreenIntent()
}