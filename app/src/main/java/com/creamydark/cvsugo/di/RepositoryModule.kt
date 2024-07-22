package com.creamydark.cvsugo.di

import android.content.Context
import com.creamydark.cvsugo.data.repository.StudentDataRepositoryImpl
import com.creamydark.cvsugo.data.repository.UniversityRepositoryImpl
import com.creamydark.cvsugo.data.repository.UserLoginDataStoreRepoImpl
import com.creamydark.cvsugo.domain.repository.StudentDataRepository
import com.creamydark.cvsugo.domain.repository.UniversityRepository
import com.creamydark.cvsugo.domain.repository.UserLoginDataStoreRepo
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
    ):UserLoginDataStoreRepo{
        return UserLoginDataStoreRepoImpl(context)
    }

    @Provides
    @Singleton
    fun provideUniversityRepository():UniversityRepository{
        return UniversityRepositoryImpl()
    }


    @Provides
    @Singleton
    fun provideStudentDataRepository(): StudentDataRepository {
        return StudentDataRepositoryImpl()
    }

}