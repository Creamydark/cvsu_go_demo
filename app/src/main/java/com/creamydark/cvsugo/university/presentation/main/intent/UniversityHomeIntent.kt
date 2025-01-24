package com.creamydark.cvsugo.university.presentation.main.intent

sealed class UniversityHomeIntent{
    data class OnSelectCourse(val id:String):UniversityHomeIntent()
}