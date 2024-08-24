package com.creamydark.cvsugo.community.feed.presentation.feedlist.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.community.feed.domain.data.PostData
import com.creamydark.cvsugo.community.feed.domain.repository.FeedRepository
import com.creamydark.cvsugo.community.feed.presentation.feedlist.intent.FeedListScreenIntent
import com.creamydark.cvsugo.community.feed.presentation.feedlist.state.FeedListScreenState
import com.creamydark.cvsugo.googleAuth.domain.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedListViewModel @Inject constructor(
    private val feedRepository: FeedRepository,
    private val accountRepository: AccountRepository
):ViewModel() {


    var state by mutableStateOf(FeedListScreenState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(loading = true)
            feedRepository.getPosts().collectLatest {
                result: Result<List<PostData>> ->
                result.onSuccess {
                    data: List<PostData> ->
                    state = state.copy(feedList = data, loading = false)
                }
            }
        }
        viewModelScope.launch {
            accountRepository.getFirebaseUser().collectLatest {
                firebaseUser->
                accountRepository.getUserData(firebaseUser?.uid?:"").collectLatest {
                        result->
                    result.onSuccess {
                            userData->
                        state = state.copy(currentUser = userData)
                    }
                }
            }
        }
    }

    fun onIntent(intent: FeedListScreenIntent){
        when (intent) {
            is FeedListScreenIntent.OnDeletePost -> {
                deletePost(intent.postData)
            }
            is FeedListScreenIntent.OnEditPost -> {

            }

            else -> {}
        }
    }
    fun deletePost(postData: PostData){
        viewModelScope.launch {
            feedRepository.deletePost(postData).collectLatest {
                result->
                result.onSuccess {

                }
                result.onFailure {

                }
            }
        }
    }
}
