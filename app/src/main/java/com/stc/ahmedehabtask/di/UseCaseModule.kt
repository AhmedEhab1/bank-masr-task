package com.stc.ahmedehabtask.di

import com.stc.domain.repo.PostsRepo
import com.stc.domain.usecase.GetLatest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideUseCase(postsRepo: PostsRepo): GetLatest{
        return GetLatest(postsRepo)
    }
}