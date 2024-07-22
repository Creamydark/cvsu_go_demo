package com.creamydark.cvsugo.domain.repository

import com.creamydark.cvsugo.domain.dataclass.AcademicInformation
import com.creamydark.cvsugo.domain.dataclass.StudentInformation
import com.creamydark.cvsugo.domain.dataclass.SubjectData

interface StudentDataRepository {
    fun getInformation(studentId: String): StudentInformation
    fun getAcademicInformation(studentId: String): AcademicInformation
    fun getSubjectEnrolledList(studentId: String): List<SubjectData>
}