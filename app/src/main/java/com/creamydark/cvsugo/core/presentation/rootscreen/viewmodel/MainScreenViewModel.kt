package com.creamydark.cvsugo.core.presentation.rootscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.auth.domain.repository.UserLoginDataStoreRepo
import com.creamydark.cvsugo.core.domain.enums.AuthenticationState
import com.creamydark.cvsugo.core.presentation.rootscreen.MainScreenEvent
import com.creamydark.cvsugo.core.presentation.rootscreen.intent.MainScreenIntent
import com.creamydark.cvsugo.core.presentation.state.MainScreenState
import com.creamydark.cvsugo.googleAuth.domain.repository.AccountRepository
import com.creamydark.cvsugo.googleAuth.domain.repository.SignInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val userLoginDataStoreRepo: UserLoginDataStoreRepo,
    private val signInRepository: SignInRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state

    private val _event = MutableSharedFlow<MainScreenEvent>()
    val event: SharedFlow<MainScreenEvent> = _event

    init {
        handleIntent(MainScreenIntent.LoadUser)
        handleIntent(MainScreenIntent.LoadLoginState)
    }

    fun handleIntent(intent: MainScreenIntent) {
        viewModelScope.launch {
            when (intent) {
                is MainScreenIntent.LoadUser -> {
                    signInRepository.currentUserListener().collectLatest { firebaseUser ->
                        if (firebaseUser == null) {
                            _state.update { it.copy(authenticationState = AuthenticationState.Unauthenticated) }
                        }
                        _state.update { it.copy(firebaseUser = firebaseUser) }

                        accountRepository.getUserData(firebaseUser?.uid ?: "").collectLatest { result ->
                            result.onSuccess { data ->
                                _state.update {
                                    it.copy(
                                        currentUser = data,
                                        authenticationState = if (data == null) AuthenticationState.OnRegister else AuthenticationState.Authenticated
                                    )
                                }
                            }
                        }
                    }
                }
                is MainScreenIntent.LoadLoginState -> {
                    userLoginDataStoreRepo.getLoginState().collectLatest { value ->
                        _state.update { it.copy(portalAuthState = value) }
                    }
                }
                is MainScreenIntent.SetLoginState -> {
                    userLoginDataStoreRepo.updateLoginState(intent.state).collectLatest {
                        if (intent.state){
                            _state.update { it.copy(portalAuthState = AuthenticationState.Authenticated) }
                        }else{
                            _state.update { it.copy(portalAuthState = AuthenticationState.Unauthenticated) }
                        }
                    }
                }
                is MainScreenIntent.SignOut -> {
                    accountRepository.signOut().collectLatest { result ->
                        result.onSuccess {
                            _state.update {
                                it.copy(
                                    authenticationState = AuthenticationState.Unauthenticated,
                                    firebaseUser = null,
                                    currentUser = null
                                )
                            }
                            _event.emit(MainScreenEvent.NavigateToUniversity)
                        }
                        result.onFailure {
                            _event.emit(MainScreenEvent.ShowError(it.message ?: "Unknown error"))
                        }
                    }
                }
            }
        }
    }
}
