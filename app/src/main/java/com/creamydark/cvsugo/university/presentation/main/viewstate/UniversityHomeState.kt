package com.creamydark.cvsugo.university.presentation.main.viewstate

import com.creamydark.cvsugo.university.domain.dataclass.CoursesOfferedData
import com.creamydark.cvsugo.university.domain.dataclass.UniversityStatsData

data class UniversityHomeState(
    val partnersLogoUrl: List<String> = emptyList(),
    val coursesOffered: List<CoursesOfferedData> = emptyList(),
    val universityStatsData: UniversityStatsData = UniversityStatsData(),
    val isLoading: Boolean = false,
    val isErrorLoading: Boolean = false
)

