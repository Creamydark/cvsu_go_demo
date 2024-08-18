package com.creamydark.cvsugo.googleAuth.di

import com.creamydark.cvsugo.googleAuth.data.repository.SignInRepositoryImpl
import com.creamydark.cvsugo.googleAuth.domain.repository.SignInRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
internal object GoogleAuthRepositoryModule {


    @Provides
    @ViewModelScoped
    fun signInRepository(): SignInRepository {
        return SignInRepositoryImpl()
    }


}