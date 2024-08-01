package com.creamydark.cvsugo.portal.presentation.studentportal.dashboard.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.auth.domain.repository.UserLoginDataStoreRepo
import com.creamydark.cvsugo.core.domain.enums.AuthenticationState
import com.creamydark.cvsugo.portal.domain.repository.StudentDataRepository
import com.creamydark.cvsugo.portal.presentation.studentportal.dashboard.state.StudentPortalDashboardScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentPortalViewModel @Inject constructor(
    private val provideStudentDataRepository: StudentDataRepository,
    private val authRepository: UserLoginDataStoreRepo
):ViewModel() {

    private fun getStudentData() = provideStudentDataRepository.getInformation("")
    private fun getAcademicInformation() = provideStudentDataRepository.getAcademicInformation("")
    private fun getSubjectEnrolledKist() = provideStudentDataRepository.getSubjectEnrolledList("")


    var state by mutableStateOf(StudentPortalDashboardScreenState())
        private set

    init {
        state = state.copy(
            studentInformation = getStudentData(),
            academicInformation = getAcademicInformation(),
            subjectEnrolledList = getSubjectEnrolledKist()
        )
        viewModelScope.launch {
            authRepository.getLoginState().collectLatest {
                value: AuthenticationState ->
                state = state.copy(authenticationState = value)
            }
        }
    }
}