package com.creamydark.cvsugo.core.presentation.rootscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.auth.domain.repository.UserLoginDataStoreRepo
import com.creamydark.cvsugo.core.domain.enums.AuthenticationState
import com.creamydark.cvsugo.googleAuth.domain.repository.SignInRepository
import com.creamydark.cvsugo.googleAuth.domain.dataclass.UserData
import com.creamydark.cvsugo.googleAuth.domain.repository.AccountRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val _firebaseUser = MutableStateFlow<FirebaseUser?>(null)
    val firebaseUser: StateFlow<FirebaseUser?> get() = _firebaseUser

    private val _currentUser = MutableStateFlow<UserData?>(null)
    val currentUser: StateFlow<UserData?> get() = _currentUser

    private val _authenticationState = MutableStateFlow(AuthenticationState.Loading)
    val authenticationState: StateFlow<AuthenticationState> get() = _authenticationState

    init {
        viewModelScope.launch {
            signInRepository.currentUserListener().collectLatest {
                firebaseUser: FirebaseUser? ->
                _firebaseUser.update { firebaseUser }



                accountRepository.getUserData(firebaseUser?.uid?:"").collectLatest {
                    result: Result<UserData?> ->
                    result.onSuccess {
                        data: UserData? ->
                        _currentUser.update { data }
                        if (data == null){
                            _authenticationState.update { AuthenticationState.OnRegister }
                        }else{
                            _authenticationState.update { AuthenticationState.Authenticated }
                        }
                    }
                }
            }
        }
        viewModelScope.launch {
            userLoginDataStoreRepo.getLoginState().collectLatest {
                value: AuthenticationState ->
//                delay(2000)
                _authenticationState.update { value }
            }
        }
    }
    fun setLoginState (state: Boolean) {
        viewModelScope.launch {
            userLoginDataStoreRepo.updateLoginState(state).collect {
                // do nothing
            }
        }
    }
}