package com.creamydark.cvsugo.portal.presentation.studentportal.dashboard.state

import com.creamydark.cvsugo.core.domain.enums.AuthenticationState
import com.creamydark.cvsugo.portal.domain.dataclass.AcademicInformation
import com.creamydark.cvsugo.portal.domain.dataclass.StudentInformation
import com.creamydark.cvsugo.portal.domain.dataclass.SubjectData

data class StudentPortalDashboardScreenState(
    val studentInformation: StudentInformation = StudentInformation(),
    val academicInformation: AcademicInformation = AcademicInformation(),
    val subjectEnrolledList: List<SubjectData> = emptyList(),
    val authenticationState: AuthenticationState = AuthenticationState.Loading
)
