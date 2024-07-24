package com.creamydark.cvsugo.core.di

import android.content.Context
import com.creamydark.cvsugo.accountsignin.data.repository.UserLoginDataStoreRepoImpl
import com.creamydark.cvsugo.accountsignin.domain.repository.UserLoginDataStoreRepo
import com.creamydark.cvsugo.announcement.data.repository.AnnouncementRepoImpl
import com.creamydark.cvsugo.announcement.domain.repository.AnnouncementRepo
import com.creamydark.cvsugo.home.data.repository.UniversityRepositoryImpl
import com.creamydark.cvsugo.home.domain.repository.UniversityRepository
import com.creamydark.cvsugo.portal.data.repository.StudentDataRepositoryImpl
import com.creamydark.cvsugo.portal.domain.repository.StudentDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun provideUserLoginDataStoreRepository(
        @ApplicationContext context: Context
    ): UserLoginDataStoreRepo {
        return UserLoginDataStoreRepoImpl(context)
    }

    @Provides
    @Singleton
    fun provideUniversityRepository(): UniversityRepository {
        return UniversityRepositoryImpl()
    }


    @Provides
    @Singleton
    fun provideStudentDataRepository(): StudentDataRepository {
        return StudentDataRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideAnnouncementRepository(): AnnouncementRepo {
        return AnnouncementRepoImpl()
    }

}