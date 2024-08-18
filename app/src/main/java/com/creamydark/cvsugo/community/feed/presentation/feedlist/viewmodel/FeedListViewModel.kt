package com.creamydark.cvsugo.community.feed.presentation.feedlist.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.creamydark.cvsugo.community.feed.presentation.feedlist.intent.FeedListScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedListViewModel @Inject constructor(

):ViewModel() {
    var state by mutableStateOf(FeedListScreenState())
        private set
}