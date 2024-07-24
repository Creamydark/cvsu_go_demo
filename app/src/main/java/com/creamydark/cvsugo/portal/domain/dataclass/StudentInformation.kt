package com.creamydark.cvsugo.portal.domain.dataclass


data class StudentInformation(
    val studentNumber: String,
    val lastName: String,
    val firstName: String,
    val middleName: String? = null,
    val suffix: String? = null,
    val email: String? = null,
    val sexByBirth: String? = null,
    val birthday: String? = null,
    val religion: String? = null,
    val civilStatus: String? = null,
    val guardianName: String? = null,
    val guardianContactNumber: String? = null
)
