package com.creamydark.cvsugo.portal.domain.dataclass

data class AcademicInformation(
    val enrolled: Boolean = false,
    val course: String = "",
    val year: Int = 0,
    val section: Int = 0,
    val currentAcademicYear: String = "",
    val currentSemester: String = "",
    val scholarship: Boolean = false
)
