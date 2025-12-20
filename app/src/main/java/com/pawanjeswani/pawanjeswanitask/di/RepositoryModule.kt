package com.pawanjeswani.pawanjeswanitask.di

import com.pawanjeswani.pawanjeswanitask.data.repository.HoldingsRepositoryImpl
import com.pawanjeswani.pawanjeswanitask.domain.repository.HoldingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindHoldingsRepository(
        holdingsRepositoryImpl: HoldingsRepositoryImpl
    ): HoldingsRepository
}
