package com.creamydark.cvsugo.university.presentation.main.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.creamydark.cvsugo.university.domain.repository.UniversityRepository
import com.creamydark.cvsugo.university.presentation.main.intent.UniversityHomeIntent
import com.creamydark.cvsugo.university.presentation.main.viewstate.UniversityHomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val universityRepository: UniversityRepository
):ViewModel() {

    var state by mutableStateOf(UniversityHomeState())
        private set


    init {
        state = state.copy(
            partnersLogoUrl = universityRepository.getPartnersLogoUrl(),
            coursesOffered = universityRepository.getCoursesOffered(),
            universityStatsData = universityRepository.getUniversityStatus()
        )
    }



    fun processIntent(intent: UniversityHomeIntent){

    }
}