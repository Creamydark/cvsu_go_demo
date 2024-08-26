package com.creamydark.cvsugo.community.feed.presentation.createpost.presentation.vewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.community.feed.domain.data.PostData
import com.creamydark.cvsugo.community.feed.domain.repository.FeedRepository
import com.creamydark.cvsugo.community.feed.presentation.createpost.presentation.intent.CreatePostScreenIntent
import com.creamydark.cvsugo.community.feed.presentation.createpost.presentation.state.CreatePostScreenState
import com.creamydark.cvsugo.googleAuth.domain.repository.AccountRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostScreenViewmodel @Inject constructor(
    private val feedRepository: FeedRepository,
    private val accountRepository: AccountRepository
): ViewModel() {

    private var currentUser by mutableStateOf<FirebaseUser?>(null)

    init {
        viewModelScope.launch {
            accountRepository.getFirebaseUser().collectLatest {
                currentUser = it
            }
        }
    }


    private val channel = Channel<Result<String>>()
    val receiver = channel.receiveAsFlow()

    var state by mutableStateOf(CreatePostScreenState())
        private set

    fun onEvent(event: CreatePostScreenIntent){
        when(event){
            is CreatePostScreenIntent.OnContentChanged -> {
                state = state.copy(content = event.content)
            }
            is CreatePostScreenIntent.OnPublishClicked -> {
                publish(state.attachments)
            }

            is CreatePostScreenIntent.OnGetImages -> {
                state = state.copy(attachments = state.attachments + event.images)
            }
            is CreatePostScreenIntent.OnRemoveImage -> {

                state = state.copy(attachments = state.attachments.filterIndexed { index, uri ->  index != event.index})

            }
        }
    }

    private fun publish(images:List<Uri>){
        state = state.copy(
            loadingPublishBTN = true
        )
        viewModelScope.launch {

            val data = PostData(
                userId = currentUser?.uid?:"unknown",
                content = state.content,
                visibility = state.visibility,
            )

            feedRepository.createPost(data,images).collectLatest {
                value: Result<String> ->
                value.onSuccess {
                    Log.d("CreatePostScreenViewmodel", "publish:$it")
                }
                value.onFailure {
                    Log.d("CreatePostScreenViewmodel", "publish:${it.message} ")
                }
                state = state.copy(
                    loadingPublishBTN = false
                )
            }
        }
    }

}