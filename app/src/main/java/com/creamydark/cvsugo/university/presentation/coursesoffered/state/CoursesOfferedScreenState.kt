package com.creamydark.cvsugo.university.presentation.coursesoffered.state

import com.creamydark.cvsugo.university.domain.dataclass.CoursesOfferedData

data class CoursesOfferedScreenState(
    val coursesOfferedList: List<CoursesOfferedData> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)