package com.creamydark.cvsugo.domain.dataclass

data class AcademicInformation(
    val enrolled: Boolean,
    val course: String,
    val year: Int,
    val section: Int,
    val currentAcademicYear: String,
    val currentSemester: String,
    val scholarship: Boolean
)
