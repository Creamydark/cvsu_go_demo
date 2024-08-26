package com.creamydark.cvsugo.community.feed.presentation.feedlist.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.creamydark.cvsugo.community.feed.domain.data.PostData
import com.creamydark.cvsugo.community.feed.domain.repository.FeedRepository
import com.creamydark.cvsugo.community.feed.presentation.feedlist.intent.FeedListScreenIntent
import com.creamydark.cvsugo.community.feed.presentation.feedlist.state.FeedListScreenState
import com.creamydark.cvsugo.googleAuth.domain.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedListViewModel @Inject constructor(
    private val feedRepository: FeedRepository,
    private val accountRepository: AccountRepository
):ViewModel() {



    var state by mutableStateOf(FeedListScreenState())
        private set

    private val _itemsData = MutableStateFlow<PagingData<PostData>>(PagingData.empty())
    val itemsData: StateFlow<PagingData<PostData>> get() = _itemsData

    init {

        loadData()

        viewModelScope.launch {
            state = state.copy(loading = true)
            accountRepository.getFirebaseUser().collectLatest {
                firebaseUser->
                accountRepository.getUserData(firebaseUser?.uid?:"").collectLatest {
                        result->
                    result.onSuccess {
                            userData->
                        state = state.copy(currentUser = userData)
                        state = state.copy(loading = false)
                    }
                }
            }
        }

    }

    private fun loadData(){
        viewModelScope.launch {
            state = state.copy(isRefreshing = true)
            feedRepository.getPostsUsingPager()
                .distinctUntilChanged()
                .cachedIn(viewModelScope).collectLatest {
                _itemsData.value = it
                state = state.copy(isRefreshing = false)
            }
        }
    }

    fun onIntent(intent: FeedListScreenIntent){
        when (intent) {
            is FeedListScreenIntent.OnDeletePost -> {
                deletePost(intent.postData)
            }

            FeedListScreenIntent.OnRefresh -> {
                loadData()
            }
            else -> {}
        }
    }
    private fun deletePost(postData: PostData){
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
