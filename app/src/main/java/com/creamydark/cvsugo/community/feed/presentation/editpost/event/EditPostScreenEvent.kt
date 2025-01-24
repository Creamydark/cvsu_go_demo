package com.creamydark.cvsugo.community.feed.presentation.editpost.event

sealed class EditPostScreenEvent {
    object UpdateFailure : EditPostScreenEvent()
    object UpdateSuccess : EditPostScreenEvent()
    object LoadingFailed : EditPostScreenEvent()
}