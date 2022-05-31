package com.matteozajac.airqualityexcercise.logic

import com.matteozajac.airqualityexcercise.entities.AQStation
import com.matteozajac.airqualityexcercise.logic.repositories.AQStationsRepository
import javax.inject.Inject
import javax.inject.Singleton

interface LoadStationsUseCase {
    suspend fun execute(): List<AQStation>
}

@Singleton
class LoadStationsInteractor @Inject constructor(private val aqStationsRepository: AQStationsRepository) : LoadStationsUseCase {
    override suspend fun execute(): List<AQStation> {
        return aqStationsRepository.getAll()
    }
}

sealed class LoadStationsException: Exception() {
    object InvalidData: LoadStationsException()
}