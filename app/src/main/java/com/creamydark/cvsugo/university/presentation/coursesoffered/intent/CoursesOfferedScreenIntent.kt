package com.creamydark.cvsugo.university.presentation.coursesoffered.intent

sealed class CoursesOfferedScreenIntent {
    data class NavigateToDetail(val id:String):CoursesOfferedScreenIntent()
}