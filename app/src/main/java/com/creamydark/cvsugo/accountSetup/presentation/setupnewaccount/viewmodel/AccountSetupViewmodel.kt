package com.creamydark.cvsugo.accountSetup.presentation.setupnewaccount.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.accountSetup.presentation.setupnewaccount.intent.AccountSetupScreenIntent
import com.creamydark.cvsugo.accountSetup.presentation.setupnewaccount.state.AccountSetupScreenState
import com.creamydark.cvsugo.googleAuth.domain.dataclass.UserData
import com.creamydark.cvsugo.googleAuth.domain.repository.AccountRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountSetupViewmodel @Inject constructor(
    private val accountRepository: AccountRepository,

    ): ViewModel() {
    private val _firebaseUser = MutableStateFlow<FirebaseUser?>(null)


    var state by mutableStateOf(AccountSetupScreenState())
        private set


    init {
        viewModelScope.launch {
            accountRepository.getFirebaseUser().collectLatest {
                user: FirebaseUser? ->
                state = state.copy(profilePictureUri = user?.photoUrl?.toString())
                _firebaseUser.update { user }
            }
        }
    }

    fun onIntent(intent: AccountSetupScreenIntent){
        when(intent){

            AccountSetupScreenIntent.OnSaveClicked -> {
                state = state.copy(loading = true)
                viewModelScope.launch {
                    val userData = UserData(
                        username = state.username,
                        uid = _firebaseUser.value?.uid,
                        profilePictureUri = state.profilePictureUri,
                        email = _firebaseUser.value?.email
                    )
                    accountRepository.createAccount(userData = userData).collectLatest {
                        result: Result<String> ->
                        result.onSuccess {

                        }
                        result.onFailure {
                            state = state.copy(error = it.message)
                        }
                        state = state.copy(loading = false)
                    }
                }
            }

            is AccountSetupScreenIntent.OnUsernameChanged -> {
                state = state.copy(username = intent.username)
            }

            is AccountSetupScreenIntent.OnProfilePictureChanged -> {

            }
        }
    }
}