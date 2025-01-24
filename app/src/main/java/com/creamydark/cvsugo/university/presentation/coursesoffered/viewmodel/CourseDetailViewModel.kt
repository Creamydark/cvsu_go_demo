package com.creamydark.cvsugo.university.presentation.coursesoffered.viewmodel

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.creamydark.cvsugo.R
import com.creamydark.cvsugo.university.domain.dataclass.CourseData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject




@HiltViewModel
class CourseDetailViewModel @Inject constructor(
    private val application: Application,
    savedStateHandle: SavedStateHandle
):ViewModel() {

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

    val courseId = savedStateHandle.get<String>("id")?:""

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

    init {
        _currentCourseData.update {
            coursesList.first {
                it.id == courseId
            }
        }
    }




}