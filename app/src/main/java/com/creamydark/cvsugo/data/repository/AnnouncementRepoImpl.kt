package com.creamydark.cvsugo.data.repository

import com.creamydark.cvsugo.domain.dataclass.AnnouncementData
import com.creamydark.cvsugo.domain.repository.AnnouncementRepo
import java.util.UUID
import javax.inject.Inject

class AnnouncementRepoImpl @Inject constructor() : AnnouncementRepo {


    val list = listOf(
        AnnouncementData(
            id = UUID.randomUUID().toString(),
            title = "CvSU AchievesHigher Education Accreditation!",
            message = "Congratulations! CvSU has been granted Level IV accreditation by [Accreditation Body]. This reflects our commitment to quality education."
        ),
        AnnouncementData(
            id = UUID.randomUUID().toString(),
            title = "New Online Enrollment System Launched",
            message= "Enroll for the upcoming semester with ease using our new online enrollment portal. Sign in for more details and to start your enrollment process."
        ),
        AnnouncementData(
            id = UUID.randomUUID().toString(),
            title = "Upcoming Career Fair: Get Ready to Network!",
            message = "Don't miss the annual CvSU Career Fair on 00/00/0000. Connect with top employers and explore exciting career opportunities. Prepare your resumes and dress to impress!"
        )
    )

    override fun getAnnouncements(): List<AnnouncementData> {
        return list
    }
}