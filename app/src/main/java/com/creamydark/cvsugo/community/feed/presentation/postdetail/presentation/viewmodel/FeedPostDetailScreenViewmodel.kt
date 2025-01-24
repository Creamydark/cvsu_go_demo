package com.creamydark.cvsugo.community.feed.presentation.postdetail.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.community.feed.domain.data.PostData
import com.creamydark.cvsugo.community.feed.domain.repository.FeedRepository
import com.creamydark.cvsugo.community.feed.presentation.postdetail.presentation.event.FeedPostDetailScreenEvent
import com.creamydark.cvsugo.community.feed.presentation.postdetail.presentation.intent.FeedPostDetailScreenIntent
import com.creamydark.cvsugo.community.feed.presentation.postdetail.presentation.state.FeedPostDetailScreenState
import com.creamydark.cvsugo.googleAuth.domain.repository.AccountRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedPostDetailScreenViewmodel@Inject constructor(
    private val accountRepository: AccountRepository,
    private val feedRepository: FeedRepository,
    savedStateHandle: SavedStateHandle
): ViewModel()  {

    private val postId = savedStateHandle.get<String>("postId")?:""

    var state by mutableStateOf(FeedPostDetailScreenState())
        private set

    private val channel = Channel<FeedPostDetailScreenEvent>()
    val receiver = channel.receiveAsFlow()

    init {
        viewModelScope.launch {
            state = state.copy(loading = true)
            accountRepository.getFirebaseUser().collectLatest {
                value: FirebaseUser? ->
                feedRepository.getSpecificPost(postId).collectLatest {
                    result->
                    result.onSuccess {
                        if (it.userData.uid == value?.uid){
                            state = state.copy(showEditButton = true)
                        }
                        state = state.copy(loading = false, post = it)
                    }
                    result.onFailure {
                        state = state.copy(loading = false)
                    }
                }
            }
        }
    }

    fun onIntent(intent: FeedPostDetailScreenIntent){

        when(intent){
            FeedPostDetailScreenIntent.Delete -> {
                delete()
            }

            FeedPostDetailScreenIntent.OnRefresh -> {
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
                    state = state.copy(loading = false)
                }
            }
        }
    }

    private fun delete(){
        viewModelScope.launch {
            feedRepository.deletePost(state.post ?: PostData()).collectLatest {
                result->
                result.onSuccess {
                    channel.trySend(FeedPostDetailScreenEvent.DeleteSuccess)
                }
                result.onFailure {
                    channel.trySend(FeedPostDetailScreenEvent.DeleteFailure)
                }
            }
        }
    }

}