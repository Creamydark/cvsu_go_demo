package com.creamydark.cvsugo.portal.domain.repository

import com.creamydark.cvsugo.portal.domain.dataclass.AcademicInformation
import com.creamydark.cvsugo.portal.domain.dataclass.StudentInformation
import com.creamydark.cvsugo.portal.domain.dataclass.SubjectData

interface StudentDataRepository {
    fun getInformation(studentId: String): StudentInformation
    fun getAcademicInformation(studentId: String): AcademicInformation
    fun getSubjectEnrolledList(studentId: String): List<SubjectData>
}