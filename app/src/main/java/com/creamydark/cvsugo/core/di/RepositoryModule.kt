package com.creamydark.cvsugo.core.di

import android.content.Context
import com.creamydark.cvsugo.auth.data.repository.UserLoginDataStoreRepoImpl
import com.creamydark.cvsugo.auth.domain.repository.UserLoginDataStoreRepo
import com.creamydark.cvsugo.notification.data.repository.NotificationRepositoryImpl
import com.creamydark.cvsugo.notification.domain.repository.NotificationRepository
import com.creamydark.cvsugo.portal.data.repository.StudentDataRepositoryImpl
import com.creamydark.cvsugo.portal.domain.repository.StudentDataRepository
import com.creamydark.cvsugo.university.data.repository.UniversityRepositoryImpl
import com.creamydark.cvsugo.university.domain.repository.UniversityRepository
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
    fun provideNotificationRepository(): NotificationRepository{
        return NotificationRepositoryImpl()
    }
}