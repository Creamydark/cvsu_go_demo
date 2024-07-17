package com.creamydark.cvsugo.presentation.screens.coursesoffered.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.creamydark.cvsugo.domain.dataclass.CoursesOfferedData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CoursesOfferedViewModel @Inject constructor():ViewModel() {
    private val coursesofferedList = listOf(
        CoursesOfferedData(id = "BSP" ,courseName = "Bachelor of Science in Psychology", imgUrl = "http://generaltrias.cvsu.edu.ph/images/course/BSP.jpg", bgColor = Color(0xFF9944c9)),
        CoursesOfferedData(id = "BSIT" ,courseName = "Bachelor of Science in Information Technology", imgUrl = "http://generaltrias.cvsu.edu.ph/images/course/BSIT.jpg", bgColor = Color(0xFF18c9eb)),
        CoursesOfferedData(id = "BSBM" ,courseName = "Bachelor of Science in Business Management", imgUrl = "http://generaltrias.cvsu.edu.ph/images/course/BSBM.jpg", bgColor = Color(0xff2d5d2b)),
        CoursesOfferedData(id = "BSHM" ,courseName = "Bachelor of Science in Hotel Management", imgUrl = "http://generaltrias.cvsu.edu.ph/images/course/BSHM.jpg", bgColor = Color(0xff7e5c48)),
        CoursesOfferedData(id = "BSOA" ,courseName = "Bachelor of Science in Office Management", imgUrl = "http://generaltrias.cvsu.edu.ph/images/course/BSOA.jpg", bgColor = Color(0xff95241c)),
        CoursesOfferedData(id = "BSTM" ,courseName = "Bachelor of Science in Tourism Management", imgUrl = "http://generaltrias.cvsu.edu.ph/images/course/BSTM.jpg", bgColor = Color(0xff7e5c48)),
        CoursesOfferedData(id = "BSE" ,courseName = "Bachelor of Science in Secondary Education", imgUrl = "http://generaltrias.cvsu.edu.ph/images/course/BSE.jpg", bgColor = Color(0xff1e175e))
    )
    private val _coursesOfferList = MutableStateFlow(coursesofferedList)
    val coursesOfferList: StateFlow<List<CoursesOfferedData>> get() = _coursesOfferList
}