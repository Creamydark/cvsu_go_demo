package com.creamydark.cvsugo.domain.repository

import com.creamydark.cvsugo.domain.dataclass.AnnouncementData

interface AnnouncementRepo {
    fun getAnnouncements():List<AnnouncementData>
}