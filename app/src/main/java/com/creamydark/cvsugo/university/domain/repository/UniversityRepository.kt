package com.creamydark.cvsugo.university.domain.repository

import com.creamydark.cvsugo.university.domain.dataclass.CoursesOfferedData
import com.creamydark.cvsugo.university.domain.dataclass.UniversityStatsData


interface UniversityRepository {
    fun getCoursesOffered(): List<CoursesOfferedData>
    fun getPartnersLogoUrl(): List<String>
    fun getUniversityStatus(): UniversityStatsData
}