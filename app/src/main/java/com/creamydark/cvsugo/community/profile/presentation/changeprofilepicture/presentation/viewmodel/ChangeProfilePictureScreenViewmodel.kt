package com.creamydark.cvsugo.community.profile.presentation.changeprofilepicture.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.googleAuth.domain.dataclass.UserData
import com.creamydark.cvsugo.googleAuth.domain.repository.AccountRepository
import com.creamydark.cvsugo.community.profile.presentation.changeprofilepicture.presentation.intent.ChangeProfilePictureScreenIntent
import com.creamydark.cvsugo.community.profile.presentation.changeprofilepicture.presentation.state.ChangeProfilePictureScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeProfilePictureScreenViewmodel @Inject constructor(
    private val accountRepository: AccountRepository
):ViewModel(){

    var state by mutableStateOf(ChangeProfilePictureScreenState())
        private set


    init {
        state = state.copy(loading = true)
        viewModelScope.launch {
            accountRepository.getFirebaseUser().collectLatest {
                user ->
                state = state.copy(firebaseUser = user)
                accountRepository.getUserData(user?.uid ?: "").collectLatest {
                        result ->
                    state = state.copy(userData = result.getOrNull() ?: UserData())
                    state = state.copy(loading = false)
                }
            }
        }
    }

    fun onIntent(intent: ChangeProfilePictureScreenIntent){
        when (intent) {

            is ChangeProfilePictureScreenIntent.Update -> {
                update()
            }

            is ChangeProfilePictureScreenIntent.SelectImage -> {
                state = state.copy(selectedImage = intent.image)
            }

        }
    }


    fun update(){
        state = state.copy(uploading = true)
        state.selectedImage?.let {
            image->
            viewModelScope.launch {
                accountRepository.updateProfilePicture(image,state.userData).collectLatest {
                    result ->
                    result.onSuccess {

                    }
                    result.onFailure {

                    }
                    state = state.copy(uploading = false)
                }
            }
        }
    }

}