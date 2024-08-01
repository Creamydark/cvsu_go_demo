package com.creamydark.cvsugo.university.presentation.coursesoffered.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.creamydark.cvsugo.university.domain.repository.UniversityRepository
import com.creamydark.cvsugo.university.presentation.coursesoffered.intent.CoursesOfferedScreenIntent
import com.creamydark.cvsugo.university.presentation.coursesoffered.state.CoursesOfferedScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoursesOfferedViewModel @Inject constructor(
    private val provideUniversityRepository: UniversityRepository
):ViewModel() {

    var state by mutableStateOf(CoursesOfferedScreenState())
        private set

    init {
        provideUniversityRepository.getCoursesOffered().let {
            state = state.copy(
                coursesOfferedList = it
            )
        }
    }

    fun execIntent(intent:CoursesOfferedScreenIntent){
        when(intent){
            is CoursesOfferedScreenIntent.NavigateToDetail -> {
//                channel.trySend(UIActions.NavigateTo(intent.id))
            }
        }
    }
}