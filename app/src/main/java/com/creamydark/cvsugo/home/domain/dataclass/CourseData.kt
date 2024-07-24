package com.creamydark.cvsugo.home.domain.dataclass

data class CourseData(
    val coursName:String ="",
    val chairperson:String = "",
    val department:String = "",
    val programSummary:String = "",
    val id:String = "",
    val totalUnits :Int = 0,
    val contactHours:Int = 0,
    val courseBannerImgUrl:String = "",
)
