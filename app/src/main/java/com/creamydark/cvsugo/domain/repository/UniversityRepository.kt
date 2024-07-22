package com.creamydark.cvsugo.domain.repository

import com.creamydark.cvsugo.domain.dataclass.CoursesOfferedData
import com.creamydark.cvsugo.domain.dataclass.UniversityStatsData


interface UniversityRepository {
    fun getCoursesOffered(): List<CoursesOfferedData>
    fun getPartnersLogoUrl(): List<String>
    fun getUniversityStatus(): UniversityStatsData
}