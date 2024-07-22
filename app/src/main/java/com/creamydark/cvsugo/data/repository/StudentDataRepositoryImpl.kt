package com.creamydark.cvsugo.data.repository

import com.creamydark.cvsugo.domain.dataclass.AcademicInformation
import com.creamydark.cvsugo.domain.dataclass.StudentInformation
import com.creamydark.cvsugo.domain.dataclass.SubjectData
import com.creamydark.cvsugo.domain.repository.StudentDataRepository
import javax.inject.Inject



class StudentDataRepositoryImpl @Inject constructor(): StudentDataRepository {
    val academicInfo = AcademicInformation(
        course = "BSIT",
        year = 1,
        section = 3,
        currentAcademicYear = "2023-2024",
        currentSemester = "1st",
        scholarship = true,
        enrolled = false
    )
    override fun getInformation(studentId: String): StudentInformation {
        return StudentInformation(
            firstName = "Marc Luis",
            lastName = "Segunto",
            studentNumber = "12345678"
        )
    }

    override fun getAcademicInformation(studentId: String): AcademicInformation {
        return academicInfo
    }

    override fun getSubjectEnrolledList(studentId: String): List<SubjectData> {
        val subjects = listOf(
            SubjectData(
                subjectName = "Art Appreciation",
                instructorName = "Iv**y Pa****",
                schedCode = "202401356",
                subjectCode = "GNED 01",
                section = "BSIT 1-3"
            ),
            SubjectData(
                subjectName = "Mathematics in the Modern World",
                instructorName = "Ro**** Si*** S. D****",
                schedCode = "202401357",
                subjectCode = "GNED 03",
                section = "BSIT 1-3"
            ),
            SubjectData(
                subjectName = "Science, Technology and Society",
                instructorName = "Ke**** D****",
                schedCode = "202401358",
                subjectCode = "GNED 06",
                section = "BSIT 1-3"
            ),
            SubjectData(
                subjectName = "Dalumat Ng/Sa Filipino",
                instructorName = "Ch******* D. N******",
                schedCode = "202401359",
                subjectCode = "GNED 12",
                section = "BSIT 1-3"
            ),
            SubjectData(
                subjectName = "Computer Programming 2",
                instructorName = "Ja******* Po*********",
                schedCode = "202401360",
                subjectCode = "DCIT 23",
                section = "BSIT 1-3"
            ),
            SubjectData(
                subjectName = "Web System and Technologies 1",
                instructorName = "Ma***** J. Be***",
                schedCode = "202401361",
                subjectCode = "ITEC 50",
                section = "BSIT 1-3"
            ),
            SubjectData(
                subjectName = "Fitness Exercises",
                instructorName = "Ni** I. Ar****",
                schedCode = "202401362",
                subjectCode = "FITT 2",
                section = "BSIT 1-3"
            ),
            SubjectData(
                subjectName = "CWTS/LTS/ROTC",
                instructorName = "Ar*** Sh**** de** To****",
                schedCode = "202401363",
                subjectCode = "NSTP 2",
                section = "BSIT 1-3"
            )
        )
        return subjects
    }
}