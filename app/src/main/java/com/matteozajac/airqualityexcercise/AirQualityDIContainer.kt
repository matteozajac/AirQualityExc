package com.matteozajac.airqualityexcercise

import com.matteozajac.airqualityexcercise.data.repositories.*
import com.matteozajac.airqualityexcercise.logic.*
import com.matteozajac.airqualityexcercise.logic.repositories.AQStationsRepository
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

    @InstallIn(SingletonComponent::class)
    @Module
    abstract class Data {
        @Binds
        abstract fun bindAQStationsRepository(
            aqStationsRepository: AQStationsRepositoryImpl
        ): AQStationsRepository

        @Binds
        abstract fun bindLocalAQStationsDataSource(
            localAQStationsDataSource: StaticLocalAQStationsDataSource
        ): LocalAQStationsDataSource


        @Binds
        abstract fun bindRemoteAQStationsDataSource(
            remoteAQStationsDataSource: AirlyRemoteAQStationsDataSource
        ): RemoteAQStationsDataSource
    }
}