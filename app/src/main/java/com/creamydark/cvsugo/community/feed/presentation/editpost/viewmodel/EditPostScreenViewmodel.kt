package com.creamydark.cvsugo.community.feed.presentation.editpost.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.community.feed.domain.data.PostData
import com.creamydark.cvsugo.community.feed.domain.repository.FeedRepository
import com.creamydark.cvsugo.community.feed.presentation.editpost.event.EditPostScreenEvent
import com.creamydark.cvsugo.community.feed.presentation.editpost.intent.EditPostScreenIntent
import com.creamydark.cvsugo.community.feed.presentation.editpost.state.EditPostScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPostScreenViewmodel @Inject constructor(
    private val feedRepository: FeedRepository,
    savedStateHandle: SavedStateHandle
):ViewModel() {

    private val postId = savedStateHandle.get<String>("postId")?:""

    var state by mutableStateOf(EditPostScreenState())
        private set

    private val channel = Channel<EditPostScreenEvent>()
    val receiver = channel.receiveAsFlow()

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
                    channel.trySend(EditPostScreenEvent.LoadingFailed)

                }
            }
        }
    }



    fun onIntent(intent: EditPostScreenIntent){
        when (intent) {
            is EditPostScreenIntent.OnContentChanged -> {
                state = state.copy(post = state.post?.copy(content = intent.content))
            }
            EditPostScreenIntent.OnPublishClicked -> {
                update()
            }

            EditPostScreenIntent.OnRefresh -> {
                refresh()
            }
        }
    }

    private fun refresh(){
        viewModelScope.launch {
            state = state.copy(loading = true)
            feedRepository.getSpecificPost(postId).collectLatest {
                    result->
                result.onSuccess {
                    state = state.copy(loading = false, post = it)
                }
                result.onFailure {
                    channel.trySend(EditPostScreenEvent.LoadingFailed)
                    state = state.copy(loading = false)

                }
            }
        }
    }

    private fun update(){
        viewModelScope.launch {
            state = state.copy(updating = true)

            feedRepository.updatePost(state.post?: PostData()).collectLatest {
                result->
                result.onSuccess {
                    state = state.copy(updating = false)
                    channel.trySend(EditPostScreenEvent.UpdateSuccess)
                }
                result.onFailure {
                    state = state.copy(updating = false)
                    channel.trySend(EditPostScreenEvent.UpdateFailure)
                }
            }
        }
    }
}