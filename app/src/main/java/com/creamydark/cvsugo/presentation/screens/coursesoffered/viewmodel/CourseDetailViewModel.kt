package com.creamydark.cvsugo.presentation.screens.coursesoffered.viewmodel

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.cvsugo.R
import com.creamydark.cvsugo.domain.dataclass.CourseData
import com.creamydark.cvsugo.domain.dataclass.CoursesOfferedData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseDetailViewModel @Inject constructor(val application: Application):ViewModel() {
    /*private val coursesofferedList = listOf(
        CoursesOfferedData(id = "BSP" ,courseName = "Bachelor of Science in Psychology", imgUrl = "http://generaltrias.cvsu.edu.ph/images/course/BSP.jpg", bgColor = Color(0xFF9944c9)),
        CoursesOfferedData(id = "BSIT" ,courseName = "Bachelor of Science in Information Technology", imgUrl = "http://generaltrias.cvsu.edu.ph/images/course/BSIT.jpg", bgColor = Color(0xFF18c9eb)),
        CoursesOfferedData(id = "BSBM" ,courseName = "Bachelor of Science in Business Management", imgUrl = "http://generaltrias.cvsu.edu.ph/images/course/BSBM.jpg", bgColor = Color(0xff2d5d2b)),
        CoursesOfferedData(id = "BSHM" ,courseName = "Bachelor of Science in Hospitality Management", imgUrl = "http://generaltrias.cvsu.edu.ph/images/course/BSHM.jpg", bgColor = Color(0xff7e5c48)),
        CoursesOfferedData(id = "BSOA" ,courseName = "Bachelor of Science in Office Management", imgUrl = "http://generaltrias.cvsu.edu.ph/images/course/BSOA.jpg", bgColor = Color(0xff95241c)),
        CoursesOfferedData(id = "BSTM" ,courseName = "Bachelor of Science in Tourism Management", imgUrl = "http://generaltrias.cvsu.edu.ph/images/course/BSTM.jpg", bgColor = Color(0xff7e5c48)),
        CoursesOfferedData(id = "BSE" ,courseName = "Bachelor of Science in Secondary Education", imgUrl = "http://generaltrias.cvsu.edu.ph/images/course/BSE.jpg", bgColor = Color(0xff1e175e))
    )*/


    private val coursesList = listOf(
        CourseData(
            coursName = "Bachelor of Science in Psychology",
            chairperson = "Ms. Shaine C. Hayag",
            department = "Department of Arts and Science",
            programSummary = "",
            id = "BSP",
            courseBannerImgUrl = "http://generaltrias.cvsu.edu.ph/images/course/BSP.jpg"
        ),
        CourseData(
            coursName = "Bachelor of Science in Information Technology",
            chairperson = "Mr. Michael E. Pareja",
            department = "Department of Information Technology",
            programSummary = "",
            id = "BSIT",
            courseBannerImgUrl = "http://generaltrias.cvsu.edu.ph/images/course/BSIT.jpg"
        ),
        CourseData(
            coursName = "Bachelor of Science in Business Management",
            chairperson = "Ms. April G. Gile",
            department = "Department of Management Studies",
            programSummary = "",
            id = "BSBM",
            courseBannerImgUrl = "http://generaltrias.cvsu.edu.ph/images/course/BSBM.jpg"
        ),
        CourseData(
            coursName = "Bachelor of Science in Hospitality Management",
            chairperson = "Mr. Aljevin A. Comiso",
            department = "Department of Hospitality and Tourism Studies",
            programSummary = "",
            id = "BSHM",
            courseBannerImgUrl = "http://generaltrias.cvsu.edu.ph/images/course/BSHM.jpg"
        ),
        CourseData(
            coursName = "Bachelor of Science in Office Management",
            chairperson = "Ms. April G. Gile",
            department = "Department of Management Studies",
            programSummary = "",
            id = "BSOA",
            courseBannerImgUrl = "http://generaltrias.cvsu.edu.ph/images/course/BSOA.jpg"
        ),
        CourseData(
            coursName = "Bachelor of Science in Tourism Management",
            chairperson = "Mr. Aljevin A. Comiso",
            department = "Department of Hospitality and Tourism Studies",
            programSummary = "",
            id = "BSTM",
            courseBannerImgUrl = "http://generaltrias.cvsu.edu.ph/images/course/BSTM.jpg"
        ),
        CourseData(
            coursName = "Bachelor of Science in Secondary Education",
            chairperson = "Ms. Aleli B. Diato",
            department = "Department of Teachers Education",
            programSummary = application.resources.getString(R.string.bse_summary),
            id = "BSE",
            courseBannerImgUrl = "http://generaltrias.cvsu.edu.ph/images/course/BSE.jpg"
        ),
    )

    private val _coursesMutableList = MutableStateFlow<List<CourseData>>(coursesList)
    val coursesMutableList: StateFlow<List<CourseData>> get() = _coursesMutableList


    private val _currentCourseData = MutableStateFlow(
        CourseData(
            coursName = "Lorem ipsum",
            chairperson = "Jang Wonyoung",
            department = "Lorem ipsum",
            programSummary = "",
            id = "null",
            totalUnits = 0,
            contactHours = 0,
            courseBannerImgUrl = "https://kpopping.com/documents/34/2/1440/240624-WONYOUNG-INSTAGRAM-UPDATE-documents-4(1).jpeg?v=c7f9c"
        ),
    )
    val currentCourseData: StateFlow<CourseData> get() = _currentCourseData

    fun setCourseData(id: String) {
        viewModelScope.launch {
            coursesList.forEach {
                data->
                if (data.id == id){
                    _currentCourseData.update {
                        data
                    }
                }
            }
        }
    }


}