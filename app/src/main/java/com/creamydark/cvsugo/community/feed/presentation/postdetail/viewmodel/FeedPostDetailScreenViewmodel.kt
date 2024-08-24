package com.creamydark.cvsugo.community.feed.presentation.postdetail.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.community.feed.domain.repository.FeedRepository
import com.creamydark.cvsugo.community.feed.presentation.postdetail.state.FeedPostDetailScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedPostDetailScreenViewmodel@Inject constructor(
    private val feedRepository: FeedRepository,
    savedStateHandle: SavedStateHandle
): ViewModel()  {

    private val postId = savedStateHandle.get<String>("postId")?:""

    var state by mutableStateOf(FeedPostDetailScreenState())
        private set


    init {
        viewModelScope.launch {
            state = state.copy(loading = true)
            feedRepository.getSpecificPost(postId).collectLatest {
                result->
                result.onSuccess {
                    state = state.copy(loading = false, post = it)
                }
                result.onFailure {
                    state = state.copy(loading = false)
                }
            }
        }
    }




}