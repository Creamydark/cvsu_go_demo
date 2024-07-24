package com.creamydark.cvsugo.home.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import com.creamydark.cvsugo.home.domain.dataclass.CoursesOfferedData
import com.creamydark.cvsugo.home.domain.dataclass.UniversityStatsData
import com.creamydark.cvsugo.home.domain.repository.UniversityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val universityRepository: UniversityRepository
):ViewModel() {

    private fun partnersLogoUrl() = universityRepository.getPartnersLogoUrl()
    private fun coursesOffered() = universityRepository.getCoursesOffered()
    private fun universityStatsData() = universityRepository.getUniversityStatus()

    private val _partnersLogoUrl = MutableStateFlow(partnersLogoUrl())
    val partnersLogoUrl: StateFlow<List<String>> get() = _partnersLogoUrl

    private val _coursesOfferList = MutableStateFlow(coursesOffered())
    val coursesOfferList: StateFlow<List<CoursesOfferedData>> get() = _coursesOfferList


    private val _universityStatsData = MutableStateFlow(universityStatsData())
    val universityStatsData: StateFlow<UniversityStatsData> get() = _universityStatsData

}