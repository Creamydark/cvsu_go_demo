package com.creamydark.cvsugo.university.domain.dataclass

import androidx.compose.ui.graphics.Color

data class CoursesOfferedData(
    val id:String = "",
    val courseName:String = "",
    val imgUrl:String = "",
    val bgColor :Color = Color(0xFF202020)
)
