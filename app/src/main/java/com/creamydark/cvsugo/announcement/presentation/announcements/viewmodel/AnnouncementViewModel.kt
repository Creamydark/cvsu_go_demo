package com.creamydark.cvsugo.announcement.presentation.announcements.viewmodel

import androidx.lifecycle.ViewModel
import com.creamydark.cvsugo.announcement.domain.dataclass.AnnouncementData
import com.creamydark.cvsugo.announcement.domain.repository.AnnouncementRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class AnnouncementViewModel @Inject constructor(
    private val provideAnnouncementRepo: AnnouncementRepo
):ViewModel() {
    private val _announcementList = MutableStateFlow<List<AnnouncementData>>(emptyList())
    val announcementList: StateFlow<List<AnnouncementData>> get() = _announcementList

    init {
        _announcementList.update {
            provideAnnouncementRepo.getAnnouncements()
        }
    }


}