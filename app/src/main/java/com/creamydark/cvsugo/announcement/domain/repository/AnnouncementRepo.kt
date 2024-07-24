package com.creamydark.cvsugo.announcement.domain.repository

import com.creamydark.cvsugo.announcement.domain.dataclass.AnnouncementData

interface AnnouncementRepo {
    fun getAnnouncements():List<AnnouncementData>
}