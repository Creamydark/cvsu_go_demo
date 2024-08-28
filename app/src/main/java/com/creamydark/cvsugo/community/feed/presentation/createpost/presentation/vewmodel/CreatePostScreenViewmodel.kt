package com.creamydark.cvsugo.community.feed.presentation.createpost.presentation.vewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.creamydark.cvsugo.community.feed.domain.data.PostData
import com.creamydark.cvsugo.community.feed.domain.repository.FeedRepository
import com.creamydark.cvsugo.community.feed.presentation.createpost.presentation.intent.CreatePostScreenIntent
import com.creamydark.cvsugo.community.feed.presentation.createpost.presentation.state.CreatePostScreenState
import com.creamydark.cvsugo.community.feed.workmanager.CreatePostWorkManager
import com.creamydark.cvsugo.googleAuth.domain.repository.AccountRepository
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostScreenViewmodel @Inject constructor(
    private val feedRepository: FeedRepository,
    private val accountRepository: AccountRepository,
    @ApplicationContext private val context: Context

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
//                publish(state.attachments)
                createPost(
                    PostData(
                        userId = currentUser?.uid?:"unknown",
                        content = state.content,
                        visibility = state.visibility,
                    ),
                    state.attachments
                )
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
                    channel.send(Result.success(it))
                }
                value.onFailure {
                    channel.send(Result.failure(it))
                }
                state = state.copy(
                    loadingPublishBTN = false
                )
            }
        }
    }


    private fun createPost(data: PostData,images: List<Uri>) {

        val jsonData = Gson().toJson(data)

        val imageUriStrings = images.map { it.toString() }.toTypedArray()

        val workData = Data.Builder()
            .putString("post_data", jsonData)
            .putStringArray("image_uris", imageUriStrings)
            .build()

        val uploadWorkRequest = OneTimeWorkRequest.Builder(CreatePostWorkManager::class.java)
            .setInputData(workData)
            .build()

        val uploadWork = WorkManager.getInstance(context)
        uploadWork.enqueue(uploadWorkRequest)

        viewModelScope.launch {
            uploadWork.getWorkInfoByIdLiveData(uploadWorkRequest.id).observeForever {
                workInfo ->
                when (workInfo.state) {
                    WorkInfo.State.RUNNING ->{
                        state = state.copy(
                            loadingPublishBTN = true
                        )
                    }
                    WorkInfo.State.SUCCEEDED -> {
                        state = state.copy(
                            loadingPublishBTN = false
                        )
                        channel.trySend(Result.success("Upload successful"))
                    }
                    WorkInfo.State.FAILED -> {
                        state = state.copy(
                            loadingPublishBTN = false
                        )
                        channel.trySend(Result.failure(Exception("Upload failed")))
                    }
                    WorkInfo.State.ENQUEUED -> {
                        state = state.copy(
                            loadingPublishBTN = true
                        )
                    }
                    else -> {
                        state = state.copy(
                            loadingPublishBTN = false
                        )
                    }
                }
            }
        }
    }

}