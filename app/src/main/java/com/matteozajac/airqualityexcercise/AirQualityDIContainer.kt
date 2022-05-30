package com.matteozajac.airqualityexcercise

import com.matteozajac.airqualityexcercise.logic.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AirQualityDIContainer {

    @InstallIn(SingletonComponent::class)
    @Module
    abstract class Logic {
        @Binds
        abstract fun bindLoadStationsUseCase(
            loadStationsImpl: LoadStationsInteractor
        ): LoadStationsUseCase

    }
}