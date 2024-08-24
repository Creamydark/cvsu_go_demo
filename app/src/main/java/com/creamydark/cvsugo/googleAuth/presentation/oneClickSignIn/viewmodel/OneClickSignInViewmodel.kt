package com.creamydark.cvsugo.googleAuth.presentation.oneClickSignIn.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.auth.util.SignInResult
import com.creamydark.cvsugo.googleAuth.domain.repository.SignInRepository
import com.creamydark.cvsugo.googleAuth.presentation.oneClickSignIn.intent.OneClickSignInScreenIntent
import com.creamydark.cvsugo.googleAuth.presentation.oneClickSignIn.state.OneClickSignInScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OneClickSignInViewmodel @Inject constructor(
    private val signInRepository: SignInRepository
): ViewModel() {
    var state by mutableStateOf(OneClickSignInScreenState())
        private set


    private val channel = Channel<SignInResult>()

    val receiver = channel.receiveAsFlow()


    fun execIntent(intent: OneClickSignInScreenIntent){
        when (intent) {
            is OneClickSignInScreenIntent.OnTokenIdReceived -> {
                signIn(intent.tokenId)
            }

            is OneClickSignInScreenIntent.OnDialogDismissed -> {
                channel.trySend(SignInResult.Error(Exception(intent.error)))
            }
            else ->{

            }
        }
    }

    fun signIn(tokenId:String){
        state = state.copy(isLoading = true)

        viewModelScope.launch {
            signInRepository.oneTapSignIn(tokenId).collectLatest { result ->
                channel.trySend(result)
                state = state.copy(isLoading = false)
            }
        }


        /*val userEmail = getUserFromTokenId(tokenId)?.email
        if (userEmail?.endsWith("@cvsu.edu.ph") != true) {
            channel.trySend(SignInResult.Error(Exception("Please use a CVSU email account")))
            state = state.copy(isLoading = false)
        } else {
            viewModelScope.launch {
                signInRepository.oneTapSignIn(tokenId).collectLatest { result ->
                    channel.trySend(result)
                    state = state.copy(isLoading = false)
                }
            }
        }*/
    }
}