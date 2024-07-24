package com.creamydark.cvsugo.portal.presentation.studentportal.defaultscreen.viewmodel

import androidx.lifecycle.ViewModel
import com.creamydark.cvsugo.portal.domain.dataclass.AcademicInformation
import com.creamydark.cvsugo.portal.domain.dataclass.StudentInformation
import com.creamydark.cvsugo.portal.domain.dataclass.SubjectData
import com.creamydark.cvsugo.portal.domain.repository.StudentDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class StudentPortalViewModel @Inject constructor(
    private val provideStudentDataRepository: StudentDataRepository
):ViewModel() {
    private fun getStudentData() = provideStudentDataRepository.getInformation("")
    private fun getAcademicInformation() = provideStudentDataRepository.getAcademicInformation("")
    private fun getSubjectEnrolledKist() = provideStudentDataRepository.getSubjectEnrolledList("")

    private val _studentInformation = MutableStateFlow<StudentInformation>(getStudentData())
    val studentInformation: StateFlow<StudentInformation> get() = _studentInformation

    private val _academicInformation = MutableStateFlow<AcademicInformation>(getAcademicInformation())
    val academicInformation: StateFlow<AcademicInformation> get() = _academicInformation

    private val _subjectEnrolledList = MutableStateFlow<List<SubjectData>>(getSubjectEnrolledKist())
    val subjectEnrolledList: StateFlow<List<SubjectData>> get() = _subjectEnrolledList
}